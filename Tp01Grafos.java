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

      File file = new File("graph-test-100.txt");
      Scanner sc = new Scanner(System.in);
      int numTotalVertices = 0;

      try{
         Scanner entrada = new Scanner(file);
         numTotalVertices = entrada.nextInt();
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

      System.out.println("Qual método para testar: ");
      System.out.println("Digite 0: Método de verificar ciclos.");
      System.out.println("Digite 1: Método de verificar articulações.");
      System.out.println("Digite 2: Método de Tarjan.");

      int operacao = sc.nextInt();

      if(operacao == 0){
         ArrayList<Vertice> percorridos = new ArrayList<Vertice>();

      System.out.println("digite o vértice de inicio");
      int um = sc.nextInt();

      System.out.println("digite o vértice destino");
      int dois = sc.nextInt();
      

      System.out.println("resultado: " + acharCiclo(um, dois, grafo, false, false, grafo.getVertice(um), percorridos));
      System.out.println("(0 significa que não há ciclo)");
      System.out.println("(1 significa que há ciclo)");
      System.out.println("(3 significa houve um erro)");

      sc.close();
      }
      else if(operacao == 1){
         for(int i = 1; i < numTotalVertices; i ++){
            boolean resposta = grafo.is_Articulacao(i, numTotalVertices);
         if(resposta == true){
            System.out.print("O vértice escolhido não é uma articulação. ");
         }else
         System.out.print("O vértice escolhido é uma articulação.");
         }
      }
      

   }

   public static int acharCiclo(int inicio, int destino, Grafo grafo, boolean containsVInicio, boolean containsVDestino, Vertice atual, ArrayList<Vertice> percorridos){

      try{
         percorridos.add(atual); //adiciona o vértice atual no array de vértices percorridos
         ArrayList<Aresta> arestasSaida = atual.getArestasSaida(); //pega todas as arestas de saída do vértice atual
         for(int i=0; i<arestasSaida.size(); i++){ //precorre todos os vértices sucessores do vértice atual
            if(arestasSaida.get(i).getFim().getDado() == destino){ //caso o vértice sucessor seja o vértice de destino
               containsVDestino = true;
            }
            else if(arestasSaida.get(i).getFim().getDado() == inicio){ //caso o vértice sucessor seja o vértice de inicio
               if(containsVDestino){
                  containsVInicio = true;
                  return 1; //retorna 1 pois o ciclo foi encontrado
               }
            }
            if(percorridos.contains(arestasSaida.get(i).getFim())){
            
            }
            else{
               int result = acharCiclo(inicio, destino, grafo, containsVInicio, containsVDestino, arestasSaida.get(i).getFim(), percorridos); 
               //System.out.println(result);
               if(result == 1){ //caso tenha sido encontrado o ciclo, ou não, retorna os respectivos valores
                  return result;
               }
//                else if(result == 0){
//                   return result;
//                }
            }
         }
      }catch(Exception e){
         return 3; //retorna 3 caso tenha acontecido algum erro
      }


      return 0; //caso não tenha sido encontrado 
   }
}
