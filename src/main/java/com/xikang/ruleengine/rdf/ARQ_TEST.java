package com.xikang.ruleengine.rdf;

import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;


/**
 * @Description
 * @Author Snowman1024
 * @Date 2020/3/5 15:57
 * @Version 1.0
 **/
public class ARQ_TEST {

    public static void main(String[] args) {
//        Model model = ModelFactory.createMemModelMaker().createDefaultModel();

        Model model = ModelFactory.createDefaultModel();
        //ttl文件路径
        String path = "E:\\demo\\rule-engine-demo\\src\\main\\java\\com\\xikang\\ruleengine\\rdf\\ex002.ttl";
        model.read("/Users/kg/IdeaProjects/arq_test/src/main/java/ex002.ttl");
        //查询语句, 查询craig的电子邮件地址
        String queryString = "PREFIX ab: <http://learningsparql.com/ns/addressbook#> \n" +
                "\n" +
                "SELECT ?craig_email\n" +
                "WHERE\n" +
                "{ ab:craig ab:email ?craig_email . }";
        Query query = QueryFactory.create(queryString);
        QueryExecution qe = QueryExecutionFactory.create(query, model);
        ResultSet results = qe.execSelect();
        // 输出结果
        ResultSetFormatter.out(System.out, results, query);
    }


//    public static void main(String[] args) {
//        Model model = ModelFactory.createDefaultModel();
//        //ttl文件路径
//        model.read("/Users/kg/IdeaProjects/arq_test/src/main/java/ex002.ttl");
//        //查询语句, 查询cindy的全部属性及属性值
//        String queryString = "PREFIX ab: <http://learningsparql.com/ns/addressbook#> \n" +
//                "\n" +
//                "SELECT ?propertyName ?propertyValue\n" +
//                "WHERE\n" +
//                "{ ab:cindy ?propertyName ?propertyValue . }";
//        Query query = QueryFactory.create(queryString);
//        QueryExecution qe = QueryExecutionFactory.create(query, model);
//        ResultSet results = qe.execSelect();
//        // 输出结果
//        ResultSetFormatter.out(System.out, results, query);
//    }
}
