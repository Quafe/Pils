
package org.pilsgeschwader.furryironman.controller.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;
import org.apache.commons.dbcp.ConnectionFactory;
import org.apache.commons.dbcp.DriverManagerConnectionFactory;
import org.apache.commons.dbcp.PoolableConnection;
import org.apache.commons.dbcp.PoolableConnectionFactory;
import org.apache.commons.dbcp.PoolingDriver;
import org.apache.commons.pool.ObjectPool;
import org.apache.commons.pool.impl.GenericObjectPool;


import org.pilsgeschwader.furryironman.model.app.ApplicationConfig;

/**
 * simple convinience wrapper for the apache database connection
 * pool. this class is not used by the application itself, its usage
 * is limited to the csv export tools.
 * 
 * @author binarygamura
 */
public class DatabasePool
{
    public static final String POOL_NAME = "eve_tool";
    
    public static final String DB_DRIVER_NAME = "com.mysql.jdbc.Driver";
    
    private static final Logger logger = Logger.getLogger(DatabasePool.class.getName());
    
    public DatabasePool()
    {
        
    }
    
    public Connection getConnection() throws SQLException
    {
        return DriverManager.getConnection("jdbc:apache:commons:dbcp:"+POOL_NAME);
    }
    
    public void shutdownPool()
    {
        try
        {
            PoolingDriver driver = (PoolingDriver) DriverManager.getDriver("jdbc:apache:commons:dbcp:");
            if(driver != null)
            {
                driver.closePool(POOL_NAME);
            }
        }
        catch(SQLException ex)
        {
            logger.warning("error while closing database pool.");
        }
    }
    
    public void testSQL() throws SQLException
    {
        try(Connection connection = getConnection(); Statement statement = connection.createStatement(); ResultSet result = statement.executeQuery("SELECT 1 + 1"))
        {
        }
    }
    
    public void initPool(ApplicationConfig configuration) throws ClassNotFoundException, SQLException, PropertyNotFoundException
    {
        String host = configuration.getPropertyAsString(ApplicationConfig.DATABASE_HOST);
        String user = configuration.getPropertyAsString(ApplicationConfig.DATABASE_USER);
        String password = configuration.getPropertyAsString(ApplicationConfig.DATABASE_PASSWORD);
        String database = configuration.getPropertyAsString(ApplicationConfig.DATABASE_PASSWORD);
        int port = configuration.getPropertyAsInt(ApplicationConfig.DATABASE_PORT);
        
        Class.forName(DB_DRIVER_NAME);
        String connectURI = "jdbc:mysql://"+host+":"+port+"/"+database+"?user="+user+"&password="+password;
        ConnectionFactory connectionFactory = new DriverManagerConnectionFactory(connectURI,null);

        ObjectPool<PoolableConnection> connectionPool = new GenericObjectPool<>();
        
        PoolableConnectionFactory poolableConnectionFactory = new PoolableConnectionFactory(connectionFactory,connectionPool,null,null,false,true);
        

        Class.forName("org.apache.commons.dbcp.PoolingDriver");
        PoolingDriver driver = (PoolingDriver) DriverManager.getDriver("jdbc:apache:commons:dbcp:");
        driver.registerPool(POOL_NAME, connectionPool);

    }
}
