package com.lizzardry.temporary.dao.tables;

public class ContactTable {

    public static final String CONTACT_TABLE_NAME = "contact_table";
    public static final String CONTACT_V_TABLE_NAME = "contact_index_table";

    public static final String COL_ID = "col_contact_id";
    public static final String COL_NAME = "col_contact_name";
    public static final String COL_COMPANY = "col_contact_company";
    public static final String COL_DEPT = "col_contact_dept";
    public static final String COL_POSITION = "col_contact_position";
    public static final String COL_PHONE = "col_contact_phone";
    public static final String COL_ADDRESS = "col_contact_address";
    public static final String COL_DOC = "col_contact_doc";

    public static final String COL_V_TABLE_DOC_ID = "docid";


    public static final String CREATE_CONTACT_TABLE_QUERY = "CREATE TABLE " +
            CONTACT_TABLE_NAME +
            "(" +
            COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL_NAME + " TEXT, " +
            COL_COMPANY + " TEXT, " +
            COL_DEPT + " TEXT, " +
            COL_POSITION + " TEXT, " +
            COL_PHONE + " TEXT, " +
            COL_ADDRESS + " TEXT ," +
            COL_DOC + " TEXT "+
            "); ";


    public static final String CREATE_V_TEXT_TABLE_QUERY =
            "CREATE VIRTUAL TABLE " + CONTACT_V_TABLE_NAME +
                    " USING fts4 (content='" + CONTACT_TABLE_NAME + "', " + COL_DOC + ")";
    public static final String CREATE_V_TEXT_TABLE_TRIGGER =
                    "CREATE TRIGGER doc_trigger_bu BEFORE UPDATE ON " + CONTACT_TABLE_NAME + " BEGIN\n" +
                    "  DELETE FROM " + CONTACT_V_TABLE_NAME + " WHERE " + COL_V_TABLE_DOC_ID + "=old." + COL_ID +";\n" +
                    "END;" +
                    "CREATE TRIGGER doc_trigger_bd BEFORE DELETE ON " + CONTACT_TABLE_NAME + " BEGIN\n" +
                    "  DELETE FROM " + CONTACT_V_TABLE_NAME + " WHERE " + COL_V_TABLE_DOC_ID + "=old." + COL_ID +";\n" +
                    "END;" +
                    "CREATE TRIGGER doc_trigger_au AFTER UPDATE ON " + CONTACT_TABLE_NAME + " BEGIN\n" +
                    "  INSERT INTO " + CONTACT_V_TABLE_NAME + "(" + COL_V_TABLE_DOC_ID + ", " + COL_DOC + ") VALUES(new." + COL_ID + ", new." + COL_DOC + ");\n" +
                    "END;" +
                    "CREATE TRIGGER doc_trigger_ai AFTER INSERT ON " + CONTACT_TABLE_NAME + " BEGIN\n" +
                    "  INSERT INTO " + CONTACT_V_TABLE_NAME + "(" + COL_V_TABLE_DOC_ID + ", " + COL_DOC + ") VALUES(new." + COL_ID + ", new." + COL_DOC + ");\n" +
                    "END;";

    public static final String REBUILD_V_TEXT_TABLE_QUERY =
            "INSERT INTO " + CONTACT_V_TABLE_NAME + "(" +CONTACT_V_TABLE_NAME + ") VALUES('rebuild')";

    public static final String DROP_CONTACT_QUERY = "DROP TABLE IF EXISTS " + CONTACT_TABLE_NAME;
    public static final String DROP_INDEX_QUERY = "DROP TABLE IF EXISTS " + CONTACT_V_TABLE_NAME;

    public static final String SELECT_WITH_STRING_TICKLE_QUERY_FORMAT = "SELECT * from " +
            CONTACT_TABLE_NAME +
            " WHERE "+ COL_ID
            + " IN " +
            "(SELECT " + COL_V_TABLE_DOC_ID +
            " FROM " + CONTACT_V_TABLE_NAME +
            " WHERE " + COL_DOC + " LIKE ?)";

    public static final String INSERT_CONTACT_QUERY =
            "INSERT INTO " + CONTACT_TABLE_NAME +
                    " (" + COL_NAME + ", " + COL_COMPANY + ", " + COL_DEPT + ", " + COL_POSITION + ", " + COL_PHONE + ", " + COL_ADDRESS + ", " + COL_DOC + ") " +
                    "values (?, ?, ?, ?, ?, ?, ?)";

}
