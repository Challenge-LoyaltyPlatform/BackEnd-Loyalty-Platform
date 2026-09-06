package loyaltyplatform_modelo.src;

public abstract class Cadastro {
    //Atributos
    protected int id;
    protected String email;
    protected String dataCadastro;

    public Cadastro(int id, String email, String dataCadastro) {
        this.id = id;
        this.email = email;
        this.dataCadastro = dataCadastro;
    }

    protected Cadastro() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(String dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public abstract String getTipo();

    @Override
    public String toString() {
        return "Cadastro{" +
                "Id= " + getId() +
                ", Email= " + getEmail() +
                ", Data de Cadastro= " + getDataCadastro() +
                "}";
    }

}
