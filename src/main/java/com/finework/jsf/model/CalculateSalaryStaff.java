package com.finework.jsf.model;

import com.finework.core.ejb.entity.SysLogisticCar;
import com.finework.core.ejb.entity.SysTransportation;
import com.finework.core.ejb.entity.SysWorkunit;
import com.finework.core.util.Constants;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Administrator
 */
public class CalculateSalaryStaff {

    public  Double calculaterOTandStaffExternal(List<SysTransportation> sysTransportations, Double amtPerday, Integer transportType) {
        Double moneyWork = 0.0;
        try {
            for (SysTransportation trans : sysTransportations) {
                //พนักงานขับรถภายใน
                if (Objects.equals(Constants.TRANSPORT_TYPE_INTERNAL, transportType)) {
                    boolean ot = false;
                    if (trans.getTpOt() || trans.getTpOTTimevalue()) {
                        if (trans.getTpOt()) {
                            ot = true;
                        }
                        if (ot) {
                            SysLogisticCar car = trans.getLogisticId();
                            //item.transportCost eq 1?'ต่อเที่ยว':item.transportCost eq 2?'ต่อระยะ'
                            if (Objects.equals(car.getTransportCost(), Constants.TRANSPORT_COST_TRAVEL)) {
                                moneyWork += car.getCharterFlights();
                                trans.setWorkMoneyOT(car.getCharterFlights());
                            } else {
                                SysWorkunit workUnit = trans.getWorkunitId();
                                // 1. ใกล้ 2.ไกล Constants.WORKUNIT_DISTANCE_NEAR;Constants.WORKUNIT_DISTANCE_LONG;
                                if (Objects.equals(Constants.WORKUNIT_DISTANCE_LONG, workUnit.getDistance())) {
                                    moneyWork += car.getTransportShort();
                                    trans.setWorkMoneyOT(car.getTransportShort());
                                } else {
                                    moneyWork += car.getTransportLong();
                                    trans.setWorkMoneyOT(car.getTransportLong());
                                }
                            }
                        } else {
                            //คิดตามช่วงเวลา 
                            Double value = ((amtPerday != null ? amtPerday : 0) / 6) * trans.getTpOtTimeHours();
                            moneyWork += value;
                            trans.setWorkMoneyOT(value);
                        }
                    }
                } else {
                    SysLogisticCar car = trans.getLogisticId();
                    if (Objects.equals(car.getTransportCost(), Constants.TRANSPORT_COST_TRAVEL)) {
                        moneyWork += car.getCharterFlights();
                        trans.setWorkMoneyOT(car.getCharterFlights());
                    } else {
                        SysWorkunit workUnit = trans.getWorkunitId();
                        // 1. ใกล้ 2.ไกล Constants.WORKUNIT_DISTANCE_NEAR;Constants.WORKUNIT_DISTANCE_LONG;
                        if (Objects.equals(Constants.WORKUNIT_DISTANCE_NEAR, workUnit.getDistance())) {
                            moneyWork += car.getTransportShort();
                            trans.setWorkMoneyOT(car.getTransportShort());
                        } else {
                            moneyWork += car.getTransportLong();
                            trans.setWorkMoneyOT(car.getTransportLong());
                        }
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return moneyWork;
    }
}
