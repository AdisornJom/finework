package com.finework.core.utils;

public abstract interface Constants
{
  public static final Integer USER_TYPE_NORMAL = 1;
  public static final Integer USER_TYPE_AGENT = 2;
  public static final Integer USER_STATUS_DELETE = 0;
  public static final Integer USER_STATUS_NORMAL = 1;
  public static final Integer USER_STATUS_INACTIVE = 2;

  public static final Integer USER_TXN_TYPE_DEPOSIT = 1;
  public static final Integer USER_TXN_TYPE_WITHDRAW = 2;

  public static final Integer USER_TXN_STATUS_ONPROCESS = 1;
  public static final Integer USER_TXN_STATUS_ACCEPT = 2;
  public static final Integer USER_TXN_STATUS_REJECT = 3;
  public static final Integer USER_TXN_STATUS_CANCEL = 4;

  public static final Integer USER_FUNDS_DATA_TYPE_DEPOSIT = 1;
  public static final Integer USER_FUNDS_DATA_TYPE_WITHDRAW = 2;
  public static final Integer USER_FUNDS_DATA_TYPE_ADJUST_CASH = 3;
  public static final Integer USER_FUNDS_DATA_TYPE_POINT = 4;
  public static final Integer USER_FUNDS_DATA_TYPE_ADJUST_POINT = 7;

  public static final Integer USER_FUNDS_DATA_STS_PENDING = 1;
  public static final Integer USER_FUNDS_DATA_STS_NORMAL = 2;
  public static final Integer USER_FUNDS_DATA_STS_REJECT = 3;
  public static final Integer USER_FUNDS_DATA_STS_CANCEL = 4;

  public static final Integer API_TOP = 2;
  public static final Integer API_STAR = 3;
  public static final Integer API_VENETIAN = 5;
  public static final Integer API_WYNN = 6;
  public static final Integer API_RACINGGAMING = 8;
  public static final Integer API_LOTTO = 9;
  public static final String FTP_PATH_ADV = "/adv/";
  public static final String FTP_PATH_PRO = "/pro/";
  public static final String FTP_PATH_BANNER = "/banner/";
  public static final String FTP_PATH_SLIP = "/slip/";
  public static final String FTP_PATH_INFO = "/info/";
  public static final String EMAIL_REGISTER = "register";
  public static final String EMAIL_FORGET_PASSWORD = "forget_password";
  public static final String TABLE_API_GAME_GR = "api_game_gr";
  public static final String BILLING_NOVAT = "NOVAT";
  public static final String BILLING_CHECK = "CHECK";
  public static final String BILLING_ACC = "ACC";
  public static final String BILLING_DELIVERY = "DELIVERY";
  public static final String BILLING_GOOD_RECEIPT = "GOODRECEIPT";
  public static final String BILLING_SALES_INVOICE = "SALESINVOICE";
  public static final String CREDIT_NOTE = "CREDITNOTE";
  public static final String BILLING_B105 = "B105";
  public static final String BILLING_B106 = "B106";
  public static final String BILLING_B107 = "B107";
  public static final String BILLING_B108 = "B108";
  public static final String BILLING_B109 = "B109";
  public static final String BILLING_B110 = "B110";
  public static final String BILLING_B111 = "B111";
  public static final String BILLING_B112 = "B112";
  public static final String SEQUNCE_NO_NOVAT = "novat";
  public static final String SEQUNCE_NO_CHECK = "check";
  public static final String SEQUNCE_NO_ACC = "acc";
  public static final String SEQUNCE_NO_DELIVERY = "delivery";
  public static final String SEQUNCE_NO_GOOD_RECEIPT_SALE_INVOICE = "goodreceipt|salesinvoice";
  public static final String SEQUNCE_WHT = "wht";
  public static final String SEQUNCE_NO_QUOTATION = "QT";
  public static final String SEQUNCE_NO_B105_B111 = "B109_B111";
  public static final String SEQUNCE_NO_B112 = "B112";
  public static final String SEQUNCE_NO_B203 = "B203";
  public static final String SEQUNCE_NO_B204 = "B204";
  public static final String SEQUNCE_NO_B205 = "B205";
  public static final String SEQUNCE_NO_MANUFACTORY = "MAF";
  public static final String SEQUNCE_NO_PAYMENT_MANUFACTORY = "PMAF";
  public static final String SEQUNCE_NO_EXPENSES_MANUFACTORY = "EXPMF";
  public static final String SEQUNCE_NO_PREPARE_TRANSPORTER = "PTP";
  public static final String SEQUNCE_NO_TRANSPORTATION = "TP";
  public static final String PAYMENT_B203 = "B103";
  public static final String PAYMENT_B205 = "B105";
  public static final String CALCULATE_QUANTITY = "จำนวน";
  public static final String CALCULATE_LENGTH = "ความยาว/เมตร";
  public static final String CALCULATE_SET = "ชุด";
  public static final Integer TRANSPORT_STAFF = 1;
  public static final Integer TRANSPORT_FOLLOW_STAFF = 2;

  public static final Integer TRANSPORT_TYPE_INTERNAL = 1;
  public static final Integer TRANSPORT_TYPE_EXTERNAL = 2;

  public static final Integer TRANSPORT_COST_ROUND = 1;
  public static final Integer TRANSPORT_COST_DISTANCE = 2;

  public static final Integer TRANSPORTER_NORMAL = 1;
  public static final Integer TRANSPORTER_INVESTIGATE = 2;
  public static final Integer TRANSPORTER_PREPARE = 3;
  public static final Integer TRANSPORTER_CARRY = 4;
  public static final Integer TRANSPORTER_COMPLETE = 5;

  public static final Integer PREPARE_TRANSPORTER_CANCEL = 1;
  public static final Integer PREPARE_TRANSPORTER_INVESTIGATE = 2;
  public static final Integer PREPARE_TRANSPORTER_PREPARE = 3;
  public static final Integer PREPARE_TRANSPORTER_CARRY = 4;
  public static final Integer PREPARE_TRANSPORTER_COMPLETE = 5;
  public static final Integer PREPARE_TRANSPORTER_NOT_COMPLETE = 6;

  public static final Integer TRANSPORTATION_PRODUCTION = 1;
  public static final Integer TRANSPORTATION_SERVICE = 2;

  public static final Integer TRANSPORTATION_ON_PROCESS = 1;
  public static final Integer TRANSPORTATION_COMPLETE = 2;
  public static final Integer TRANSPORTATION_CANCEL = 3;

  public static final Integer WORKUNIT_DISTANCE_NEAR = 1;
  public static final Integer WORKUNIT_DISTANCE_LONG = 2;

  public static final Integer TRANSPORT_COST_TRAVEL = 1;
  public static final Integer TRANSPORT_COST_SPAN = 2;

  public static final Integer LOGISTIC_GROUP_TYPE_SMALL = 1;
  public static final Integer LOGISTIC_GROUP_TYPE_LARGE = 2;

  public static final Integer TRANSPORT_STAFF_SPECIAL = 1;
  public static final Integer TRANSPORT_STAFF_SPECIAL_NO_VAT = 2;
  public static final String WHT_SEQ_1 = "ค่าจ้างทำของ";
  public static final String WHT_SEQ_2 = "ค่าโฆษณา";
  public static final String WHT_SEQ_3 = "ค่าเช่า";
  public static final String WHT_SEQ_4 = "ค่าขนส่ง";
  public static final String WHT_SEQ_5 = "ค่าบริการ";
  public static final String WHT_SEQ_6 = "ค่าเบี้ยประกันวินาศภัย";
  public static final String WHT_SEQ_7 = "อื่นๆ(ระบุ) ";
  public static final Integer VAT3_PERSEN = 1;
  public static final Integer VAT1_PERSEN = 2;
}