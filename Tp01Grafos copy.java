import java.util.Scanner;

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
     
     public Integer buscaEmLargura(){
        ArrayList<Vertice> tempDescoberta = new ArrayList<Vertice>();
        ArrayList<Vertice> tempoTermino = new ArrayList<Vertice>();

        Vertice atualVertice = this.vertices.get(0);
        System.out.println("Dado do vertice atual: " + atualVertice.getDado() + "\n");

        tempDescoberta.add(atualVertice);
        tempoTermino.add(atualVertice);

        int num_vertices = 0; 
  
        while(tempoTermino.size() > 0){
           Vertice visitado = tempoTermino.get(0);
           for(int i =0; i < visitado.getArestasSaida().size(); i++){
              Vertice vizinhoVertice = visitado.getArestasSaida().get(i).getFim();
              
              if(!tempDescoberta.contains(vizinhoVertice)){//já foi marcado
               tempDescoberta.add(vizinhoVertice);  
               tempoTermino.add(vizinhoVertice);

                 //System.out.println(vizinhoVertice.getDado());
                 
              }
           }
           tempoTermino.remove(0);
           num_vertices++;
        }
        //System.out.println("Numero de vertices passados pela busca: " + num_vertices);
        return num_vertices;
  
     }

     //Entrada: n = numero do vertice que será removido, totalVertices = numtotal de vertices do grafo
     public  boolean is_Articulacao(Integer n, Integer totalVertices) {
      Vertice atualVertice = this.getVertice(n);
      this.vertices.remove(atualVertice); // remove o vertice do grafo
      int qnt_busca = buscaEmLargura(); //Faz a busca semo vértice e retorna a quantidade de vertices que a busca passou
      //System.out.print(qnt_busca);
      if(qnt_busca == 100){
         return true;  //Ele
      }
      else 
      return false;
     
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

      for(int i = 1; i< 100; i ++){
         boolean resposta = grafo.is_Articulacao(i, numTotalVertices);
      if(resposta == true){
         System.out.print("O vértice escolhido não é uma articulação. ");
      }else
      System.out.print("O vértice escolhido é uma articulação.");
      }
      
      

    }
}