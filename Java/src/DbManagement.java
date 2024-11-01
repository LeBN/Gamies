import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DbManagement {
    private static final String DB_URL = "jdbc:mariadb://localhost:3307/";
    private static final String DB_NAME = "Purple_Heart_DB";
    private static final String USER = "root";
    private static final String PASS = "LeBeNa2!";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL + DB_NAME, USER, PASS);
    }

    public static void initializeDatabase() {
        String createDatabaseQuery = "CREATE DATABASE IF NOT EXISTS " + DB_NAME;
        String useDatabaseQuery = "USE " + DB_NAME;

        String createOptionsTable = "CREATE TABLE IF NOT EXISTS Options (" +
                "Opt_Id INT NOT NULL AUTO_INCREMENT, " +
                "fullscreen BOOLEAN DEFAULT FALSE, " +
                "volume INT DEFAULT 50, " +
                "sound_effect INT DEFAULT 50, " +
                "fps_limit INT DEFAULT 60, " +
                "show_collision BOOLEAN DEFAULT FALSE, " +
                "PRIMARY KEY (Opt_Id))";

        String createLevelsTable = "CREATE TABLE IF NOT EXISTS Levels (" +
                "Lvl_Id INT NOT NULL AUTO_INCREMENT, " +
                "Lvl_Bg_Path VARCHAR(255), " +
                "Lvl_Collision_Path VARCHAR(255), " +
                "Lvl_Next_UpLeft INT, " +
                "Lvl_Way_UpLeft_X INT, " +
                "Lvl_Way_UpLeft_Y INT, " +
                "Lvl_Spawn_UpLeft_X INT, " +
                "Lvl_Spawn_UpLeft_Y INT, " +
                "Lvl_Next_UpRight INT, " +
                "Lvl_Way_UpRight_X INT, " +
                "Lvl_Way_UpRight_Y INT, " +
                "Lvl_Spawn_UpRight_X INT, " +
                "Lvl_Spawn_UpRight_Y INT, " +
                "Lvl_Next_DownLeft INT, " +
                "Lvl_Way_DownLeft_X INT, " +
                "Lvl_Way_DownLeft_Y INT, " +
                "Lvl_Spawn_DownLeft_X INT, " +
                "Lvl_Spawn_DownLeft_Y INT, " +
                "Lvl_Next_DownRight INT, " +
                "Lvl_Way_DownRight_X INT, " +
                "Lvl_Way_DownRight_Y INT, " +
                "Lvl_Spawn_DownRight_X INT, " +
                "Lvl_Spawn_DownRight_Y INT, " +
                "PRIMARY KEY (Lvl_Id))";

        String createItemTypesTable = "CREATE TABLE IF NOT EXISTS Item_Types (" +
                "ItmTp_Id INT NOT NULL AUTO_INCREMENT, " +
                "ItmTp_Name VARCHAR(31), " +
                "ItmTp_Description VARCHAR(511), " +
                "ItmTp_Sprites_Path VARCHAR(255), " +
                "ItmTp_Max_Count INT, " +
                "PRIMARY KEY (ItmTp_Id))";

        String createItemsTable = "CREATE TABLE IF NOT EXISTS Items (" +
                "Itm_Id INT NOT NULL AUTO_INCREMENT, " +
                "Item_Type_Id INT, " +
                "Itm_Is_In_Inventory BOOLEAN, " +
                "Item_Lvl_Id INT, " +
                "Itm_X INT, " +
                "Itm_Y INT, " +
                "PRIMARY KEY (Itm_Id), " +
                "FOREIGN KEY (Item_Type_Id) REFERENCES Item_Types(ItmTp_Id), " +
                "FOREIGN KEY (Item_Lvl_Id) REFERENCES Levels(Lvl_Id))";

        String createProofsTable = "CREATE TABLE IF NOT EXISTS Proofs (" +
                "Prf_Id INT NOT NULL AUTO_INCREMENT, " +
                "Prf_Level_Id INT, " +
                "Prf_X INT, " +
                "Prf_Y INT, " +
                "Prf_Type INT, " +
                "Prf_Name VARCHAR(63), " +
                "Prf_Information_TextBox VARCHAR(1023), " +
                "Prf_Sprite_Path VARCHAR(255), " +
                "PRIMARY KEY (Prf_Id), " +
                "FOREIGN KEY (Prf_Level_Id) REFERENCES Levels(Lvl_Id))";

        String createMonologsTable = "CREATE TABLE IF NOT EXISTS Monologs (" +
                "Mnlg_Id INT NOT NULL AUTO_INCREMENT, " +
                "Mnlg_Character_Id INT, " +
                "Mnlg_Text VARCHAR(255), " +
                "Mnlg_Next_Monolog_Id INT, " +
                "PRIMARY KEY (Mnlg_Id))";

        String createSavesTable = "CREATE TABLE IF NOT EXISTS Saves (" +
                "Sv_Id INT NOT NULL AUTO_INCREMENT, " +
                "Sv_Level_Id INT, " +
                "Sv_Proofs BINARY(8), " +
                "Sv_Items BINARY(8), " +
                "Sv_Coins INT, " +
                "PRIMARY KEY (Sv_Id), " +
                "FOREIGN KEY (Sv_Level_Id) REFERENCES Levels(Lvl_Id))";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(createDatabaseQuery);
            stmt.executeUpdate(useDatabaseQuery);
            stmt.executeUpdate(createOptionsTable);
            stmt.executeUpdate(createLevelsTable);
            stmt.executeUpdate(createItemTypesTable);
            stmt.executeUpdate(createItemsTable);
            stmt.executeUpdate(createProofsTable);
            stmt.executeUpdate(createMonologsTable);
            stmt.executeUpdate(createSavesTable);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}