import java.util.Scanner;
import javax.sound.midi.VoiceStatus;
import java.util.ArrayList;
class Vertice{
    private int dado;
    private ArrayList<Aresta> arestasEntrada;
    private ArrayList<Aresta> arestasSaida;

    public Vertice(Integer valor){
        this.dado = valor;
        this.arestasEntrada = new ArrayList<Aresta>();
        this.arestasSaida = new ArrayList<Aresta>();
     }

     public int getDado() {
         return dado;
     }
     public void setDado(int dado) {
         this.dado = dado;
     }

     public void adicionarArestaEntrada(Aresta aresta){
        this.arestasEntrada.add(aresta);
     }
  
     public void adicionarArestaSaida(Aresta aresta){
        this.arestasSaida.add(aresta);
     }

     public ArrayList<Aresta> getArestasEntrada() {
        return arestasEntrada;
    }
 
    public ArrayList<Aresta> getArestasSaida() {
        return arestasSaida;
    }

}
class Aresta{
    private Vertice fim;
    private Vertice inicio;

    public Aresta(Vertice inicio, Vertice fim){
        this.inicio = inicio;
        this.fim = fim;
     }
    
     public Vertice getFim() {
         return fim;
     }
     public Vertice getInicio() {
         return inicio;
     }

     public void setFim(Vertice fim) {
         this.fim = fim;
     }
     public void setInicio(Vertice inicio) {
         this.inicio = inicio;
     }
}
class Grafo{
    private ArrayList<Vertice> vertices;
    private ArrayList<Aresta> arestas;

    public Grafo(){
        this.vertices = new ArrayList<Vertice>();
        this.arestas = new ArrayList<Aresta>();
     }

     public void adicionarVertices(Integer dado){
        Vertice novoVertice = new Vertice(dado);
        this.vertices.add(novoVertice);
     } 

     public void adicionarArestas(Integer dadoInicio, Integer dadoFim){
        Vertice inicio = this.getVertice(dadoInicio);
        Vertice fim = this.getVertice(dadoFim);
        Aresta aresta = new Aresta(inicio, fim);
  
        inicio.adicionarArestaSaida(aresta);
        fim.adicionarArestaEntrada(aresta);
        
        this.arestas.add(aresta);
     }

     public Vertice getVertice(Integer dado){
        Vertice vertice = null;
        for(int i=0; i < this.vertices.size(); i++){
           if(this.vertices.get(i).getDado() == dado){
              vertice = this.vertices.get(i);
              break;
           }
        }
        return vertice;
     }


}
class Tp01Grafos{
    public static void main(String[] args) {
        Grafo grafo = new Grafo();

      Scanner entrada = new Scanner(System.in);
      int numTotalVertices = entrada.nextInt();
      //System.out.println(numTotalVertices);
      int numTotalArestas = entrada.nextInt();
      //System.out.println(numTotalArestas);

      for(int i=1; i < numTotalVertices + 1; i++){
         grafo.adicionarVertices(i);
      }

      for(int i = 0; i < numTotalArestas; i++){
         int numVerticeInicio = entrada.nextInt();
         //System.out.println(numVerticeInicio);
         int numVerticeSaida = entrada.nextInt();
         //System.out.println(numVerticeSaida);
         grafo.adicionarArestas(numVerticeInicio, numVerticeSaida);

      }


    }
}