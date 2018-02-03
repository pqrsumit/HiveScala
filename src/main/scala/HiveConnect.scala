/*Written by :- pqrsumit
* System:- Cloudera-qucikstart-vm-5.12.0.0
* Virtualization:- VirtualBox
* =================================================
* About Program
* This program simply print 10 records from customers table on the hive
* I have dumped all tables in the retail_db schema whcih are avaiale in
* cloudera distribution mysql database
* Use below sqoop command to get all table loaded to hive
* sqoop import-all-tables \
--connect jdbc:mysql://localhost:3306/retail_db \
--username root \
--password cloudera \
-m 1 \
--hive-import
* */

/*==============================================
* Errors Faced
* 1.HiveContext was not able to connect to metastore
*  To solve this please specify metastore.uris
*
* 2.Permission Error on /tmp/hive
*  To solve this please give permission using below commands
*  sudo -u hive hdfs dfs -chmod 777 /tmp/hive
*  I am not pretty sure how to overcome this error in production
*  environment but look like problem with version
*
*  Also execute below command to give pers=mission on local dir
*  sudo chmod 777 /tmp/hive
*
* 3. Problem with META-INF file or dependency after building project
*    To solve this please delete META-INF files using below commands.
*    */
//    zip -d ModuleName.jar 'META-INF/*.SF' 'META-INF/*.RSA' 'META-INF/*SF'
/*    Credit for this command:- Stackoverflow
*/
/*======================================================
* Build this module and execute using below given command*
* spark-submit --master yarn --class HiveConnect ./TestModule.jar/
*/
import org.apache.spark.sql.hive.HiveContext
import org.apache.spark.{SparkConf, SparkContext}
object HiveConnect{
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[10]").setAppName("HiveModule From Spark")
      .set("spark.driver.host", "localhost")
    val sc = new SparkContext(conf)

    println(sc.sparkUser)
    val hc = new HiveContext(sc)
    hc.setConf("hive.metastore.uris", "thrift://localhost:9083")
    val rdd= hc.read.table("customers")
    rdd.show(100)


  }
}