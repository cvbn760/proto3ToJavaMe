package content.domain;

import java.io.IOException;

public class Test {
    public static void main(String...args) throws IOException {
//
//        ByteService byteService = new ByteService();
////        // Создаем объект класа от генератора Дмитрия К.
////        ClientParams clientParams = ClientParams.newBuilder()
////                .setParameterId(5)
////                .setStringValue("test")
////                .setIntValue(999)
////                .setParameterName("AleX").build();
////
////        // Берем из него массив байт
////        byte[] x = clientParams.toByteArray();
//////        System.out.println(byteService.byteArrayToHexString(x, x.length));
//////
//////        // Пытаемся распарсить в такой же класс только сгенерированный на собственном генераторе
//////        my_messages.ClientParams clientParams1 = my_messages.ClientParams.parseFrom(x);
//////        System.out.println(clientParams1);
////
////        // Наоборот
////
////        ClientParams clientParamsTo = ClientParams.newBuilder()
////                .setParameterName("JfdhgugdsifiudS")
////                .setParameterId(5)
////                .setStringValue("UUUUdjhfKKKKKK")
////                .setIntValue(124)
////                .build();
////
////
////        byte[] xc = clientParamsTo.toByteArray();
////        System.out.println(byteService.byteArrayToHexString(xc, xc.length));
////        my_messages.ClientParams clientParams1 = my_messages.ClientParams.parseFrom(x);
//////        System.out.println(clientParams1);
//
//        // Сравним между собой 3 объекта
////        LadaConnect.ClientParams clientParamsLada = LadaConnect.ClientParams.newBuilder()
////                .setParameterName("Test_test_tesT")
////                .setParameterId(555)
////                .setStringValue("String_Test")
////                .setIntValue(9999999)
////                .build();
////
////        byte[] a = clientParamsLada.toByteArray();
////
////        my_messages.ClientParams clientParamsMy = ClientParams.newBuilder()
////                .setParameterName("Test_test_tesT")
////                .setParameterId(555)
////                .setStringValue("String_Test")
////                .setIntValue(9999999)
////                .build();
////        byte[] b = clientParamsMy.toByteArray();
////
////        need_messages.ClientParams clientParamsDm = need_messages.ClientParams.newBuilder()
////                .setParameterName("Test_test_tesT")
////                .setParameterId(555)
////                .setStringValue("String_Test")
////                .setIntValue(9999999)
////                .build();
////        byte[] c = clientParamsDm.toByteArray();
////
////        System.out.println(byteService.byteArrayToHexString(a, a.length));
////        System.out.println(byteService.byteArrayToHexString(b, b.length));
////        System.out.println(byteService.byteArrayToHexString(c, c.length));
//
//
//        LocationData locationDataMy = LocationData.newBuilder()
//                .setOccurrenceTime(1636716967) // Кодируется не так как для остальных вариантах
//                .setLat(90)
//                .build();
//
//        byte[] a = locationDataMy.toByteArray();
//
//        LadaConnect.LocationData test = LadaConnect.LocationData.parseFrom(a);
//        System.out.println("test " + test);
//
//        need_messages.LocationData locationDataDm = need_messages.LocationData.newBuilder()
//                .setOccurrenceTime(1636716967)
//                .setLat(90)
//                .build();
//        byte[] b = locationDataDm.toByteArray();
//
//        LadaConnect.LocationData testTo = LadaConnect.LocationData.parseFrom(b);
//        System.out.println("testTo " + testTo);
//
//        LadaConnect.LocationData locationDataLada = LadaConnect.LocationData.newBuilder()
//                //.setOccurrenceTime(1636716967)
//                .setLat(90)
//                .build();
//        byte[] c = locationDataLada.toByteArray();
//
//        System.out.println(byteService.byteArrayToHexString(a, a.length));
//        System.out.println(byteService.byteArrayToHexString(b, b.length));
//        System.out.println(byteService.byteArrayToHexString(c, c.length));
    }
}
