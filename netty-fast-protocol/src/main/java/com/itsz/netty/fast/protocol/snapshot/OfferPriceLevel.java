package com.itsz.netty.fast.protocol.snapshot;

public class OfferPriceLevel {
	
	private  int offerSize;
	
	private  int offerPx;
	
	private  int OfferOrderNumbers;

	public int getOfferSize() {
		return offerSize;
	}

	public void setOfferSize(int offerSize) {
		this.offerSize = offerSize;
	}

	public int getOfferPx() {
		return offerPx;
	}

	public void setOfferPx(int offerPx) {
		this.offerPx = offerPx;
	}

	public int getOfferOrderNumbers() {
		return OfferOrderNumbers;
	}

	public void setOfferOrderNumbers(int offerOrderNumbers) {
		OfferOrderNumbers = offerOrderNumbers;
	}

	@Override
	public String toString() {
		return "OfferPriceLevel [offerSize=" + offerSize + ", offerPx=" + offerPx + ", OfferOrderNumbers="
				+ OfferOrderNumbers + "]";
	}
	

}
