public class Main {
    public static void main(String[] args) {
        ConexionMySQL conexionMySQL = new ConexionMySQL();
        boolean conn=conexionMySQL.Conexion("jdbc:mysql://uv8e5bhcj06tdvic:ME3Yq8U3Cax9OIjbHqdq@bfgwcnxsobb1g6asgq4d-mysql.services.clever-cloud.com:3306/bfgwcnxsobb1g6asgq4d", "uv8e5bhcj06tdvic", "ME3Yq8U3Cax9OIjbHqdq");
        if (conn){
            System.out.println("Conexion establecida");
        }
        else{
            System.out.println("Conexion no establecida");
        }
    }
}
