package content.domain;

import itelma.ByteService;
import my_messages.*;


import java.io.IOException;

public class Test {
    public static void main(String...args) throws IOException {
        ByteService byteService = new ByteService();
//        need_messages.AccelData accelData = need_messages.AccelData
//                .newBuilder()
//                .addDuration(111)
//                .addDuration(222)
//                .addDuration(333)
//                .addTresholdDuration(444)
//                .addTresholdValue(555)
//                .setMaxValue(150)
//                .addDuration(456)
//                .build();
//
//        System.out.println(accelData);
//        byte[] x = accelData.toByteArray();
//        System.out.println("array: " + byteService.byteArrayToHexString(x, x.length));
//
//        AccelData accelData1 = AccelData.parsing(x);
//        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>\n\n\n\n\n\n\n");
//        System.out.println(accelData1);
//        byte[] y = accelData1.toByteArray();
//        System.out.println("array: " + byteService.byteArrayToHexString(y, y.length));

        //08960112076FDE01CD02C8031A02BC032202AB04
        //08960112076FDE01CD02C8031A02BC032202AB04

//        EraIdData eraIdData = EraIdData.newBuilder()
//                .setEraSoftVerBoot(new byte[]{0x11,0x22,0x33,0x44})
//                .setEraSoftVerMidlet(new byte[]{0x11,0x22,0x33,0x44})
//                .setEraSoftVerXC(new byte[]{0x11,0x22,0x33,0x44})
//                .setIMEI(new byte[]{0x11,0x22,0x33,0x44}).build();
//
//        byte[] x = eraIdData.toByteArray();
//        System.out.println("MY: " + byteService.byteArrayToHexString(x, x.length));
//
//        need_messages.EraIdData eraIdData1 = need_messages.EraIdData.newBuilder()
//                .setEraSoftVerBoot(new byte[]{0x11,0x22,0x33,0x44})
//                .setEraSoftVerMidlet(new byte[]{0x11,0x22,0x33,0x44})
//                .setEraSoftVerXC(new byte[]{0x11,0x22,0x33,0x44})
//                .setIMEI(new byte[]{0x11,0x22,0x33,0x44}).build();
//        byte[] y = eraIdData1.toByteArray();
//        System.out.println("NEED: " + byteService.byteArrayToHexString(y, y.length));
//
//        // 0A04112233441204112233441A0411223344220411223344
//        // 0A04112233441204112233441A0411223344220411223344

//        VehicleStatus vehicleStatus = VehicleStatus
//                .newBuilder()
//                .setEngineState(2)
//                .setBatteryVoltage(12)
//                .setAllLockStatus(10)
//                .setOutsideTemp(20)
//                .setEngineCoolantTemp(20)
//                .setInVehicleTemp(30)
//                .setFuelLevel(25)
//                .setHandBrakeStatus(15)
//                .setBrakeFluidAlarmState(5)
//                .setAverageFuelConsumption(100)
//                .setFuelConsumotionPerTrip(200)
//                .setInVehicleTemp(100)
//                .setCheckEngineState(3)
//                .setAutorunEngineErrorStatus(5)
//                .setAirTempAdj(20)
//                .setDippedBeamState(25)
//                .setFrontDefrostState(20)
//                .build();
//        byte[] x = vehicleStatus.toByteArray();
//
//        need_messages.VehicleStatus vehicleStatus1 = need_messages.VehicleStatus
//                .newBuilder()
//                .setEngineState(2)
//                .setBatteryVoltage(12)
//                .setAllLockStatus(10)
//                .setOutsideTemp(20)
//                .setEngineCoolantTemp(20)
//                .setInVehicleTemp(30)
//                .setFuelLevel(25)
//                .setHandBrakeStatus(15)
//                .setBrakeFluidAlarmState(5)
//                .setAverageFuelConsumption(100)
//                .setFuelConsumotionPerTrip(200)
//                .setInVehicleTemp(100)
//                .setCheckEngineState(3)
//                .setAutorunEngineErrorStatus(5)
//                .setAirTempAdj(20)
//                .setDippedBeamState(25)
//                .setFrontDefrostState(20)
//                .build();
//        byte[] y = vehicleStatus1.toByteArray();
//        System.out.println(vehicleStatus1);
//
//        System.out.println("MY:   " + byteService.byteArrayToHexString(x, x.length));
//        System.out.println("NEED: " + byteService.byteArrayToHexString(y, y.length));
//
//        // MY:   0802180C200A2828302838C8014019480F5005586460C80170037805800114880119900114
//        // NEED: 0802180C200A2828302838C8014019480F5005586460C80170037805800114880119900114

//        ClientParams clientParams = ClientParams
//                .newBuilder()
//                .setStringValue("TEST")
//                .setParameterId(555)
//                .setParameterName("TEST_NAME")
//                .setIntValue(111).build();
//
//        byte[] x = clientParams.toByteArray();
//
//        need_messages.ClientParams clientParams1 = need_messages.ClientParams
//                .newBuilder()
//                .setStringValue("TEST")
//                .setParameterId(555)
//                .setParameterName("TEST_NAME")
//                .setIntValue(111).build();
//        byte[] y = clientParams1.toByteArray();
//        System.out.println(clientParams1);
//
//        System.out.println("MY:   " + byteService.byteArrayToHexString(x, x.length));
//        System.out.println("NEED: " + byteService.byteArrayToHexString(y, y.length));

//        MY:   0A09544553545F4E414D4510AB041A0454455354206F
//        NEED: 0A09544553545F4E414D4510AB041A0454455354206F
//
//        ClientResponse clientResponse = ClientResponse
//                .newBuilder()
//                .setCmdId(2)
//                .setResCode(3)
//                .addCParams(clientParams)
//                .addCParams(clientParams)
//                .build();
//
//        byte[] x = clientResponse.toByteArray();
//        System.out.println(clientResponse);
//
//        need_messages.ClientResponse clientResponse1 = need_messages.ClientResponse
//                .newBuilder()
//                .setCmdId(2)
//                .setResCode(3)
//                .addClientParameter(clientParams1)
//                .addClientParameter(clientParams1)
//                .build();
//
//        byte[] y = clientResponse1.toByteArray();
//        System.out.println(clientResponse1);
//
//        System.out.println("MY:   " + byteService.byteArrayToHexString(x, x.length));
//        System.out.println("NEED: " + byteService.byteArrayToHexString(y, y.length));
//        MY:   080210031A160A09544553545F4E414D4510AB041A0454455354206F1A160A09544553545F4E414D4510AB041A0454455354206F
//        NEED: 080210031A160A09544553545F4E414D4510AB041A0454455354206F1A160A09544553545F4E414D4510AB041A0454455354206F


//        ClientParams clientParams = ClientParams
//                .newBuilder()
//                .setStringValue("TEST")
//                .setParameterId(555)
//                .setParameterName("TEST_NAME")
//                .setIntValue(111).build();
//
//
//        ServerCommand serverCommand = ServerCommand.newBuilder()
//                .setCmdAlias(2)
//                .setCmdId(2)
//                .addCParams(clientParams)
//                .build();
//
//        byte[] x = serverCommand.toByteArray();
//
//        need_messages.ClientParams clientParams1 = need_messages.ClientParams
//                .newBuilder()
//                .setStringValue("TEST")
//                .setParameterId(555)
//                .setParameterName("TEST_NAME")
//                .setIntValue(111).build();
//
//        need_messages.ServerCommand serverCommand1 = need_messages.ServerCommand.newBuilder()
//                .setCmdAlias(2)
//                .setCmdId(2)
//                .addClientParameter(clientParams1)
//                .build();
//        byte[] y = serverCommand1.toByteArray();
//        System.out.println(serverCommand1);
//
//        System.out.println("MY:   " + byteService.byteArrayToHexString(x, x.length));
//        System.out.println("NEED: " + byteService.byteArrayToHexString(y, y.length));
//        MY:   0802100222160A09544553545F4E414D4510AB041A0454455354206F
//        NEED: 0802100222160A09544553545F4E414D4510AB041A0454455354206F

//        ClientParams clientParams = ClientParams
//                .newBuilder()
//                .setStringValue("TEST")
//                .setParameterId(555)
//                .setParameterName("TEST_NAME")
//                .setIntValue(111).build();
//
//        ServerCommand serverCommand = ServerCommand.newBuilder()
//                .setCmdAlias(2)
//                .setCmdId(2)
//                .addCParams(clientParams)
//                .build();
//
//        OutcomingMsg outcomingMsg = OutcomingMsg.newBuilder()
//                .setMessageID(65556)
//                .setType(3)
//                .setTime(1232533252)
//                .setFlags(234)
//                .setFlagsHasValues(2435)
//                .setECUSpecificData(new byte[]{0x45,0x56,0x58,0x76})
//                .setIgnoreId(true)
//                .setServCommand(serverCommand)
//                .build();
//
//        byte[] x = outcomingMsg.toByteArray();
//
///////////////////
//        need_messages.ClientParams clientParams1 = need_messages.ClientParams
//                .newBuilder()
//                .setStringValue("TEST")
//                .setParameterId(555)
//                .setParameterName("TEST_NAME")
//                .setIntValue(111).build();
//
//        need_messages.ServerCommand serverCommand1 = need_messages.ServerCommand.newBuilder()
//                .setCmdAlias(2)
//                .setCmdId(2)
//                .addClientParameter(clientParams1)
//                .build();
//
//        need_messages.OutcomingMsg outcomingMsg1 = need_messages.OutcomingMsg.newBuilder()
//                .setMessageID(65556)
//                .setType(3)
//                .setTime(1232533252)
//                .setFlags(234)
//                .setFlagsHasValues(2435)
//                .setECUSpecificData(new byte[]{0x45,0x56,0x58,0x76})
//                .setIgnoreId(true)
//                .setServCommand(serverCommand1)
//                .build();
//
//        byte[] y = outcomingMsg1.toByteArray();
//        System.out.println(outcomingMsg1);
//
//        System.out.println("MY:   " + byteService.byteArrayToHexString(x, x.length));
//        System.out.println("NEED: " + byteService.byteArrayToHexString(y, y.length));
////                                                            AccelDataCondig
////        MY:   0894800410031D04F7764920EA012883133204455658760    802100222160A09544553545F4E414D4510AB041A0454455354206F
////        NEED: 0894800410031D04F7764920EA01288313320445565876521C0802100222160A09544553545F4E414D4510AB041A0454455354206F


        IncomingMsg incomingMsg = IncomingMsg
                .newBuilder()
                .setDeviceID(new byte[]{0x22,0x44,0x66, (byte) 0x88})
                .setSequenceNumber(567)
                .setType(4)
                .setTime(35436)
                .setFlags(436)
                .setFlagsHasValues(46346)
                .setECUSpecificData(new byte[]{0x22,0x44,0x66, (byte) 0x88})
                .build();

    }
}
