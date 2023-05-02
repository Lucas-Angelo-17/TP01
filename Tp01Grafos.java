import java.util.Scanner;
import javax.sound.midi.VoiceStatus;

import java.io.File;
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

     public void buscaEmProfundidade(Integer pos){
        ArrayList<Vertice> tempDescoberta = new ArrayList<Vertice>();
        ArrayList<Vertice> tempoTermino = new ArrayList<Vertice>();
        ArrayList<Vertice> pai = new ArrayList<Vertice>();
        Vertice atualVertice = this.getVertice(pos);
        tempDescoberta.add(atualVertice);
        tempoTermino.add(atualVertice);
  
        while(tempoTermino.size() > 0){
           Vertice visitado = tempoTermino.get(0);
           for(int i =0; i < visitado.getArestasSaida().size(); i++){
              Vertice vizinhoVertice = visitado.getArestasSaida().get(i).getFim();
              System.out.println(vizinhoVertice.getDado());
              if(!tempDescoberta.contains(vizinhoVertice)){//já foi marcado
                 tempoTermino.add(vizinhoVertice);
                 //buscaEmProfundidade(vizinhoVertice.getDado());
                 System.out.println(vizinhoVertice.getDado());
                 tempDescoberta.add(vizinhoVertice);
              }
           }
           tempoTermino.remove(0);
        }
  
     }


}


class Tp01Grafos{
   public static void main(String[] args) {
      Grafo grafo = new Grafo();

      File file = new File("graph-test-50000.txt");
      Scanner sc = new Scanner(System.in);

      try{
         Scanner entrada = new Scanner(file);
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

         entrada.close();
      }catch(Exception e){
         System.out.println(e.getMessage());
      }

      ArrayList<Vertice> percorridos = new ArrayList<Vertice>();

      System.out.println("digite1");
      int um = sc.nextInt();

      System.out.println("digite2");
      int dois = sc.nextInt();
      

      System.out.println("resultado: " + acharCiclo(um, dois, grafo, false, false, grafo.getVertice(um), percorridos));
      System.out.println("(0 significa que não há ciclo)");
      System.out.println("(1 significa que há ciclo)");
      System.out.println("(3 significa houve um erro)");

      sc.close();

   }

   public static int acharCiclo(int inicio, int destino, Grafo grafo, boolean containsVInicio, boolean containsVDestino, Vertice atual, ArrayList<Vertice> percorridos){

      //System.out.println("chamado: " + atual);
      try{
        // Vertice tmp = grafo.getVertice(inicio);
         // for (int j=0; j<percorridos.size(); j++) {
         //    int curr = percorridos.get(j).getDado();
         
         //    System.out.println(curr);
         // }
         percorridos.add(atual);
         ArrayList<Aresta> arestasSaida = atual.getArestasSaida();
         for(int i=0; i<arestasSaida.size(); i++){
            if(arestasSaida.get(i).getFim().getDado() == destino){
               containsVDestino = true;
            }
            else if(arestasSaida.get(i).getFim().getDado() == inicio){
               if(containsVDestino){
                  containsVInicio = true;
                  return 1;
               }
            }
            if(percorridos.contains(arestasSaida.get(i).getFim())){
               // System.out.println("dadwa");
               // System.out.println(arestasSaida.get(i).getFim().getDado());
               // System.out.println("chegou: ");
               // return 2;
               // break;
            }
            else{
               int result = acharCiclo(inicio, destino, grafo, containsVInicio, containsVDestino, arestasSaida.get(i).getFim(), percorridos);
               //System.out.println(result);
               if(result == 1){
                  return result;
               }
               else if(result == 0){
                  return result;
               }
            }
         }
      }catch(Exception e){
         return 3;
      }


      return 0;
   }
}