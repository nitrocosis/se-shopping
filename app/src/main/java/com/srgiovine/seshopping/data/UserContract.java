package com.srgiovine.seshopping.data;

import android.provider.BaseColumns;

final class UserContract implements BaseColumns {

    private UserContract() {
    }

    static final String TABLE_NAME = "USER";

    static final String ID = _ID;

    static final String EMAIL = "Email";
    static final String PASSWORD = "Password";

    static final String FIRST_NAME = "FirstName";
    static final String LAST_NAME = "LastName";

    static final String STREET = "Street";
    static final String CITY = "City";
    static final String STATE = "State";
    static final String ZIP = "Zip";
    static final String COUNTRY = "Country";

    static final String CARD_NUMBER = "CardNumber";
    static final String CARD_HOLDER_FIRST_NAME = "CardHolderFirstName";
    static final String CARD_HOLDER_LAST_NAME = "CardHolderLastName";
    static final String CARD_EXPIRATION_DATE = "CardExpirationDate";
    static final String CARD_SECURITY_CODE = "CardSecurityCode";

    static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +

            ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +

            EMAIL + " TEXT NOT NULL," +
            PASSWORD + " TEXT NOT NULL," +

            FIRST_NAME + " TEXT," +
            LAST_NAME + " TEXT," +

            STREET + " TEXT," +
            CITY + " TEXT," +
            STATE + " TEXT," +
            ZIP + " TEXT," +
            COUNTRY + " TEXT," +

            CARD_NUMBER + " TEXT," +
            CARD_HOLDER_FIRST_NAME + " TEXT," +
            CARD_HOLDER_LAST_NAME + " TEXT," +
            CARD_EXPIRATION_DATE + " TEXT," +
            CARD_SECURITY_CODE + " INTEGER," +

            " UNIQUE (" + EMAIL + ")" +
            " )";

    static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

}
