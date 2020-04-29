package com.drool.engine.entity;

import java.math.BigDecimal;

public class ItemCity {
    public enum City {PUNE, NAGPUR}

//    杂货、药品、手表、奢侈品
    public enum Type {GROCERIES, MEDICINES, WATCHES, LUXURYGOODS}

    private City purchaseCity;
    private BigDecimal sellPrice;
    private Type typeofItem;
    private BigDecimal localTax;
    private BigDecimal totalAmt;

    public ItemCity() {

    }
    public ItemCity(City city, Type goodType, BigDecimal sellPrice) {
        this.purchaseCity = city;
        this.typeofItem = goodType;
        this.sellPrice = sellPrice;
    }

    public City getPurchaseCity() {
        return purchaseCity;
    }

    public void setPurchaseCity(City purchaseCity) {
        this.purchaseCity = purchaseCity;
    }

    public BigDecimal getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(BigDecimal sellPrice) {
        this.sellPrice = sellPrice;
    }

    public Type getTypeofItem() {
        return typeofItem;
    }

    public void setTypeofItem(Type typeofItem) {
        this.typeofItem = typeofItem;
    }

    public BigDecimal getLocalTax() {
        return localTax == null ? null : localTax.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public void setLocalTax(BigDecimal localTax) {
        this.localTax = localTax;
    }

    public BigDecimal getTotalAmt() {
        if (localTax != null && sellPrice != null) {
            return localTax.add(sellPrice).setScale(2, BigDecimal.ROUND_HALF_UP);
        } else {
            return null;
        }
    }

}