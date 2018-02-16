import java.util.StringTokenizer;

public class RetiroDP {
	//Atributos
	private String nocta,tipo, fecha, hora;
	private int saldoAntes,cantidad,saldoNuevo;


	//Constructores

    public RetiroDP() {
    	this.nocta="";
    	this.tipo="";
    	this.saldoAntes=0;
    	this.cantidad=0;
    	this.saldoNuevo=0;
    	this.fecha="";
    	this.hora="";
    }

    public RetiroDP(String datos) {
	    	StringTokenizer st 	= new StringTokenizer(datos,"_");
	    	this.nocta			= st.nextToken();
	    	this.tipo			= st.nextToken();
	    	this.saldoAntes		= Integer.parseInt( st.nextToken());
	    	this.cantidad		= Integer.parseInt( st.nextToken());
	    	this.saldoNuevo		= Integer.parseInt( st.nextToken());
	    	this.fecha			= st.nextToken();
	    	this.hora   		= st.nextToken();
    }
    //Accesors
    public String getNocta(){
    	return this.nocta;
    }

    public String getTipo(){
    	return this.tipo;
    }

    public int getSaldoAntes(){
    	return this.saldoAntes;
    }

    public int getCantidad(){
    	return this.cantidad;
    }

        public int getSaldoNuevo(){
    	return this.saldoNuevo;
    }

    public String getFecha(){
    	return this.fecha;
    }

    public String getHora(){
    	return this.fecha;
    }



    //Mutators
    public void setNocta(String ncta){
    	this.nocta= ncta;
    }

    public void setTipo(String type){
    	this.tipo= type;
    }

    public void setSaldoAntes(int sal){
    	this.saldoAntes= sal;
    }

    public void setCantidad(int canti){
    	this.cantidad= canti;
    }

    public void setSaldoNuevo(int saldo){
    	this.saldoNuevo= saldo;
    }

    public void setFecha(String date){
    	this.fecha = date;
    }

    public void setHora(String hour){
    	this.hora = hour;
    }

    public String toStringSql(){
    	return "'"+this.nocta + "','" + this.tipo + "'," + this.saldoAntes + "," + this.cantidad +","+this.saldoNuevo+",'"+this.fecha+"','"+this.hora+"'";
    }

    public String toString(){
    	return this.nocta + "_" + this.tipo + "_" + this.saldoAntes + "_" + this.cantidad+"_"+this.saldoNuevo+"_"+this.fecha+"_"+this.hora;
    }


}
