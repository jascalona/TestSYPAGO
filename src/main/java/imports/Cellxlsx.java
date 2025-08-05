package imports;

import com.poiji.annotation.ExcelCell;
import com.poiji.annotation.ExcelCellName;
import com.poiji.config.Formatting;
import com.poiji.exception.HeaderMissingException;
import com.poiji.option.PoijiOptions;

/**
 *
 * @author DELL
 */
class Cellxlsx {
    private String id;
    private String nombre;
    private String codBanco;
    private String tlf;
    private String cuenta;
    private String CMCN;

    // Constructor
    public Cellxlsx(String id, String nombre, String codBanco, String tlf, String cuenta,String CMCN) {
        this.id = id;
        this.nombre = nombre;
        this.codBanco = codBanco;
        this.tlf = tlf;
        this.cuenta = cuenta;
        this.CMCN = CMCN;
    }

    // Getters para acceder a los datos
    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCodBanco() {
        return codBanco;
    }

    public String getTlf() {
        return tlf;
    }

    public String getCuenta() {
        return cuenta;
    }
    
    public String getCMNC(){
        return CMCN;
    }

    // Opcional: Método toString para una fácil impresión de los objetos
    @Override
    public String toString() {
        return "datos{" +
               "id='" + id + '\'' +
               ", nombre='" + nombre + '\'' +
               ", codBanco='" + codBanco + '\'' +
               ", tlf='" + tlf + '\'' +
               ", cuenta='" + cuenta + '\'' +
               ", CMCN=" + CMCN + '\'' +
               '}';
    }
}
