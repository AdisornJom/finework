package com.finework.core.utils;

/**
 * @author MR Aekasit Sengnualnim (Aek) System Analyst
 *
 * Progress Software Co.,Ltd Fl. 6-7 306 Supha Road, Phomphlab
 * Phomphlabsattupai, Bangkok 10100 Thailand Tel :+66 (0) 2867 5020 Mobile : +66
 * 91004 1009 Skype : s.aekasit MSN : aekasit.se@gmail.com Email :
 * aekasit.s@kasikornbank.com Website http://www.kasikornbank.com
 *
 * @create 13-05-2015 4:39:44 PM
 */
public interface Constants {

    /*
    USER
     */
    Integer USER_TYPE_NORMAL = 1;
    Integer USER_TYPE_AGENT = 2;
    Integer USER_STATUS_DELETE = 0;
    Integer USER_STATUS_NORMAL = 1;
    Integer USER_STATUS_INACTIVE = 2;

    /*
    USER_TXN
     */
    Integer USER_TXN_TYPE_DEPOSIT = 1;
    Integer USER_TXN_TYPE_WITHDRAW = 2;
    //
    Integer USER_TXN_STATUS_ONPROCESS = 1;
    Integer USER_TXN_STATUS_ACCEPT = 2;
    Integer USER_TXN_STATUS_REJECT = 3;
    Integer USER_TXN_STATUS_CANCEL = 4;

    /*
    USER_FUNDS_DATA
     */
    Integer USER_FUNDS_DATA_TYPE_DEPOSIT = 1;
    Integer USER_FUNDS_DATA_TYPE_WITHDRAW = 2;
    Integer USER_FUNDS_DATA_TYPE_ADJUST_CASH = 3;
    Integer USER_FUNDS_DATA_TYPE_POINT = 4;
    Integer USER_FUNDS_DATA_TYPE_ADJUST_POINT = 7;
    //
    Integer USER_FUNDS_DATA_STS_PENDING = 1;
    Integer USER_FUNDS_DATA_STS_NORMAL = 2;
    Integer USER_FUNDS_DATA_STS_REJECT = 3;
    Integer USER_FUNDS_DATA_STS_CANCEL = 4;

    /*
    api id follow up with table core_api_name
     */
    Integer API_TOP = 2;
    Integer API_STAR = 3;
    Integer API_VENETIAN = 5;
    Integer API_WYNN = 6;
    Integer API_RACINGGAMING = 8;
    Integer API_LOTTO = 9;
    /*
    ftp folder path
     */
    String FTP_PATH_ADV = "/adv/";
    String FTP_PATH_PRO = "/pro/";
    String FTP_PATH_BANNER = "/banner/";
    String FTP_PATH_SLIP = "/slip/";
    String FTP_PATH_INFO = "/info/";

    /*
    Email template
     */
    String EMAIL_REGISTER = "register";
    String EMAIL_FORGET_PASSWORD = "forget_password";
    /*
    seq
     */
    String TABLE_API_GAME_GR = "api_game_gr";
    
    
    //============bmcnx=============
    String BILLING_NOVAT="NOVAT";
    String BILLING_CHECK="CHECK";//วางบิล
    String BILLING_ACC="ACC";//โอนเงินเข้าบริษัท
    String BILLING_DELIVERY="DELIVERY";
    String BILLING_GOOD_RECEIPT="GOODRECEIPT";
    String BILLING_SALES_INVOICE="SALESINVOICE";
    String CREDIT_NOTE="CREDITNOTE"; //B204 ใบลดหนี้

    String BILLING_B105="B105";
    String BILLING_B106="B106";
    String BILLING_B107="B107";
    String BILLING_B108="B108";
    String BILLING_B109="B109";
    String BILLING_B110="B110";
    String BILLING_B111="B111";
    String BILLING_B112="B112";
    
    String SEQUNCE_NO_NOVAT="novat";
    String SEQUNCE_NO_CHECK="check";
    String SEQUNCE_NO_ACC="acc";
    String SEQUNCE_NO_DELIVERY="delivery";
    String SEQUNCE_NO_GOOD_RECEIPT_SALE_INVOICE="goodreceipt|salesinvoice";
    String SEQUNCE_WHT="wht";
    
    String SEQUNCE_NO_QUOTATION="QT";
    
 /*   String SEQUNCE_NO_B105_="B105";
    String SEQUNCE_NO_B106_="B106";
    String SEQUNCE_NO_B107_="B107";
    String SEQUNCE_NO_B108_="B108";*/
    //String SEQUNCE_NO_B105_B108="B105_B108";
    
  /*  String SEQUNCE_NO_B109="B109";
    String SEQUNCE_NO_B110="B110";*/
    //String SEQUNCE_NO_B109_B111="B109_B111";
    String SEQUNCE_NO_B105_B111="B109_B111";
    String SEQUNCE_NO_B112="B112";
    
    String SEQUNCE_NO_B203="B203";
    String SEQUNCE_NO_B204="B204";
    String SEQUNCE_NO_B205="B205";
    
    String SEQUNCE_NO_MANUFACTORY="MAF";
    String SEQUNCE_NO_PAYMENT_MANUFACTORY="PMAF";
    String SEQUNCE_NO_EXPENSES_MANUFACTORY="EXPMF";
    
    //Transporter
    String SEQUNCE_NO_PREPARE_TRANSPORTER="PTP";
    String SEQUNCE_NO_TRANSPORTATION="TP";
    
   // String SEQUNCE_NO_GOOD_RECEIPT="goodreceipt";
   // String SEQUNCE_NO_SALES_INVOICE="salesinvoice";
    
    //Payment Type
    String PAYMENT_B203="B103";
    String PAYMENT_B205="B105"; 
    
    
    //CalculateType Manufactory
    String CALCULATE_QUANTITY="จำนวน";
    String CALCULATE_LENGTH="ความยาว/เมตร";
    String CALCULATE_SET="ชุด";
    
    //Transport_Type
    Integer TRANSPORT_STAFF=1;
    Integer TRANSPORT_FOLLOW_STAFF=2;
    
    
    Integer TRANSPORT_TYPE_INTERNAL=1;//พนักงานขับรถภายใน
    Integer TRANSPORT_TYPE_EXTERNAL=2;//พนักงานขับรถภายนอก
    
    Integer TRANSPORT_COST_ROUND=1;
    Integer TRANSPORT_COST_DISTANCE=2;
    
    //Transporter manufactory_real  2=ตรวจสอบ, 3=เตรียมขนส่ง, 4=ดำเนินการขนส่ง, 5=ขนส่งเรียบร้อย
    Integer TRANSPORTER_NORMAL=1;
    Integer TRANSPORTER_INVESTIGATE=2;
    Integer TRANSPORTER_PREPARE=3;
    Integer TRANSPORTER_CARRY=4;
    Integer TRANSPORTER_COMPLETE=5;
    
    //Transporter prepareTransporter
                                /*  "ยกเลิก" "1" />
                                รอตรวจสอบ" "2" />
                                เตรียมขนส่ง" "3" />
                                ดำเนินการขนส่ง" "4" />
                                ขนส่งสำเร็จ" "5" />
                                ขนส่งไม่สำเร็จ" "6" /> */
    Integer PREPARE_TRANSPORTER_CANCEL=1;
    Integer PREPARE_TRANSPORTER_INVESTIGATE=2;
    Integer PREPARE_TRANSPORTER_PREPARE=3;
    Integer PREPARE_TRANSPORTER_CARRY=4;
    Integer PREPARE_TRANSPORTER_COMPLETE=5;
    Integer PREPARE_TRANSPORTER_NOT_COMPLETE=6;
    
    //Transportation Type
    Integer TRANSPORTATION_PRODUCTION=1;
    Integer TRANSPORTATION_SERVICE=2;
    
    //Transportation status 1=รอ 2=สำเร็จ 3=ไม่สำเร็จ
    Integer TRANSPORTATION_ON_PROCESS=1;
    Integer TRANSPORTATION_COMPLETE=2;
    Integer TRANSPORTATION_CANCEL=3;
    
    Integer WORKUNIT_DISTANCE_NEAR=1;//ระยะใกล้
    Integer WORKUNIT_DISTANCE_LONG=2;//ระยะไกล
    
    Integer TRANSPORT_COST_TRAVEL=1;  //ขนส่งต่อเที่ยว
    Integer TRANSPORT_COST_SPAN=2;//ขนส่งต่อระยะ
    

    Integer LOGISTIC_GROUP_TYPE_SMALL=1;//ขนาดเล็ก
    Integer LOGISTIC_GROUP_TYPE_LARGE=2;//ขนาดใหญ่
    
    Integer TRANSPORT_STAFF_SPECIAL=1;//รายได้พิเศษ ธรรมดา
    Integer TRANSPORT_STAFF_SPECIAL_NO_VAT=2;//รายได้พิเศษ ธรรมดา
    
    
    
    //WHT
    String WHT_SEQ_1="ค่าจ้างทำของ";
    String WHT_SEQ_2="ค่าโฆษณา";
    String WHT_SEQ_3="ค่าเช่า";
    String WHT_SEQ_4="ค่าขนส่ง";
    String WHT_SEQ_5="ค่าบริการ";
    String WHT_SEQ_6="ค่าเบี้ยประกันวินาศภัย";
    String WHT_SEQ_7="อื่นๆ(ระบุ) ";
    
    
    Integer VAT3_PERSEN=1;
    Integer VAT1_PERSEN=2;
}
