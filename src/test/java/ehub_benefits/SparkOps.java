package ehub_benefits;

import java.sql.ResultSet;
import java.util.Properties;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.*;


public class SparkOps {

	public SparkOps() {
		// TODO Auto-generated constructor stub
	}
	
	public static void SparkComp(ResultSet SRCrs, ResultSet TRGrs) throws ClassNotFoundException, InterruptedException {
		System.out.println("Came within SparkComp Method");
		
		//SparkConf sparkConf = new SparkConf();
		//sparkConf.setAppName("Spark Comparator");
		//sparkConf.setMaster("local");
		//JavaSparkContext context = new JavaSparkContext(sparkConf);
		
		//DB JDBC Details
		//Class.forName("oracle.jdbc.OracleDriver");
		String jdbcUrl = "jdbc:oracle:thin:@va10dx10v1-scan1:1525/EHPRDD2D";
		String User    = "ad23382";
		String pw      = "M#Y1rxGA";
		String sql = "select * from EHUB_PROD_RAW.cs90_ehub_prod where rownum < 11;";
		//"jdbc:oracle:thin:@va10dx10v1-scan1:1525/EHPRDD2D","ad23382","M#Y1rxGA"
		
		/*
		SparkSession spark = SparkSession
			    .builder()
			    .appName("My Spark App")
			    .config("option name", "option value")
			    .master("local")
			    .getOrCreate();
		
		Dataset<Row> dual = spark
                .read()
                .format( "jdbc" )
                .option( "url", jdbcUrl )
                .option( "dbtable", sql )
                .option( "password", pw )
                .option( "user", User )
                .load();
		*/
		System.setProperty("hadoop.home.dir", "C:\\Hadoop");
		JavaSparkContext sc = new JavaSparkContext(new SparkConf().setAppName("SparkJdbcDs").setMaster("local[*]"));
		SQLContext sqlContext = new SQLContext(sc);
		SparkSession sparkSess = SparkSession.builder().appName("My Spark App").getOrCreate();
		Properties connectionProperties = new Properties();
		connectionProperties.put("user", User);
		connectionProperties.put("password", pw);
		String query = "sparkour.people";
		query = "(select * from EHUB_PROD_RAW.cs90_ehub_prod where rownum < 11)";
		
		Dataset<Row> jdbcDF = sparkSess.read().jdbc(jdbcUrl, query,connectionProperties);
		jdbcDF.show();
		
		System.out.println("Created SparkSession and ran SQL ");
		//Thread.sleep(500);
		
		

		//---------------

		
		
		
		
		
		
		//---------------
		

		
		
		sparkSess.close();
		//context.close();
		System.out.println("Spark Context created and closed successfully");
		
		
		
	}
	
	

}
