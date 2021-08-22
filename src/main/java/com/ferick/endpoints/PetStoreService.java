package com.ferick.endpoints;

public final class PetStoreService {

    public static class Pet {
        public static final String BASIC = "/pet";
        public static final String BY_ID = "/pet/{petId}";
    }

    public static class Store {
        public static final String ORDER_BASIC = "/store/order";
        public static final String ORDER_BY_ID = "/store/order/{orderId}";
    }
}
