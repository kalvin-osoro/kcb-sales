//package com.deltacode.kcb.utils;
//
//import org.locationtech.jts.geom.Coordinate;
//import org.locationtech.jts.geom.GeometryFactory;
//import org.locationtech.jts.geom.LinearRing;
//import org.locationtech.jts.geom.Polygon;
//import org.springframework.data.geo.GeoModule;
//
//import java.text.DecimalFormat;
//import java.util.logging.Logger;
//
//public class InsidePolygonTest {
//
//    public static void main(String[] args) {
//        Logger logger = Logger.getLogger(InsidePolygonTest.class.getName());
//        Coordinate[] coordinates = new Coordinate[]{
//                new Coordinate(-31.414547, -64.488178),
//                new Coordinate(-31.415579, -64.496261),
//                new Coordinate(-31.411513, -64.495720),
//                new Coordinate(-31.408726, -64.489549),
//                new Coordinate(-31.414547, -64.488178)
//        };
//        GeometryFactory geometryFactory = new GeometryFactory();
//        LinearRing linearRing = geometryFactory.createLinearRing(coordinates);
//        Polygon polygon = geometryFactory.createPolygon(linearRing, null);
//
//        Coordinate point = new Coordinate(-31.413962, -64.486445);
//
//
//        if (isPointInPolygon(coordinates, point)) {
//            logger.info("Point inside polygon!");
//        } else {
//            logger.info("Point outside polygon!");
//        }
//    }
//    //create a fuction to check weather a point is inside a polygon or not
//    public static boolean isPointInPolygon(Coordinate[] coordinates, Coordinate point) {
//        GeometryFactory geometryFactory = new GeometryFactory();
//        LinearRing linearRing = geometryFactory.createLinearRing(coordinates);
//        Polygon polygon = geometryFactory.createPolygon(linearRing, null);
//        return polygon.contains(geometryFactory.createPoint(point));
//    }
//
//
//}
