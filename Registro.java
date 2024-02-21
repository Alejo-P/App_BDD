public class Registro {
    // Atributos
    private int cedula;
    private String nombre;
    private String apellido;
    private String direccion;
    private String telefono;
    private int edad;
    private String curso;
    private byte[] imagen;
    // Constructor
    public Registro() {}
    public Registro(int cedula, String nombre, String apellido, String direccion, String telefono, int edad, String curso, byte[] imagen) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.apellido = apellido;
        this.direccion = direccion;
        this.telefono = telefono;
        this.edad = edad;
        this.curso = curso;
        this.imagen = imagen;
    }
    // Metodos
    public boolean regitroCompleto(){
        if (this.cedula!=0 && !this.nombre.isEmpty() && !this.apellido.isEmpty() && !this.direccion.isEmpty() && !this.telefono.isEmpty() && !new String(String.valueOf(this.edad)).isEmpty() && !this.curso.isEmpty() && this.imagen!=null){
            return true;
        }
        else{
            return false;
        }
    }
    public int getCedula() {
        return cedula;
    }

    public void setCedula(int cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }
}
