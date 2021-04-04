package Util;

public final class ClubOlympusContract {

    public final static String DATABASE_NAME = "club_olympus_database";

    private ClubOlympusContract(){

    }

    public static final class Members{

        public final static String TABLE_NAME = "members";

        public final static String _ID = "id";
        public final static String FIRST_NAME = "first_name";
        public final static String LAST_NAME = "last_name";
        public final static String GENDER = "gender";
        public final static String KIND_OF_SPORT = "kind_of_sport";

        public final static int GENDER_UNKNOWN = 0;
        public final static int GENDER_MALE = 1;
        public final static int GENDER_FEMALE = 2;
    }

}
