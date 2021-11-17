// la gramática que va a reconocer es la siguiente

// 1.<S>      --> <E> {Resultado}             Selección(1)={(,I} 
// 2.<E>      --> +<E><E>{suma}               Selección(2)={(,I}
// 3.<E>    -->   *<E><E>{mult}               Selección(3)={+}
// 4.<E>    -->   I                           Selección (4)= I


package apcompiladorrecursivoatributos;

public class ApCompiladorRecursivoAtributos {
    
    static String cad = "(5*2) >= (9)¬";
    static String cad1 = "0123456789.";
    // variable indice es global y controla el indice del objwro lex1
    static int indice=0;
    static char sim=' ';
    static Lexico lex1 = new Lexico();
    static String cadavance="";
    
    public static void main(String[] args) {
       /* InputStreamReader isr= new InputStreamReader(System.in);
        BufferedReader flujoE = new BufferedReader(isr);*/
        analisisLexico();
        cad=lex1.cadenaLexico();
        sim=lex1.darElemento(indice).darTipo();
        cadavance=cadavance+sim;
        
        S();
        if (sim=='¬')
            System.out.println("Se acepta la secuencia ");
        else
            System.out.println("Se rechaza la secuencia ");
    
    }
    

    public static void S(){
        System.out.println("Estoy en S");
        switch (sim) {
            case 'i':case '(':
                    NoTerminal s1 = new NoTerminal("s1",0,0);
                    ELO(s1);// 1
                    resultado(s1); // i1
                    return;
            default: 
                    System.out.println("Secuencia"+cad+" no se acepta en S");
                    rechace();
        }
    }

    public static void ELO(NoTerminal s1){
        
        System.out.println("Estoy en ELO");
        switch (sim) {
            case 'i':case '(':
                    NoTerminal s2 = new NoTerminal("s2",0,0);
                    EL2(s2);// 2
                    ELOL(s2, s1);
                    //resultado(s1.getValor()); // i1`
                    break;
            default: 
                    System.out.println("Secuencia"+cad+" no se acepta en ELO");
                    rechace();
        }
    }

    public static void ELOL(NoTerminal i1, NoTerminal s1){ // PREGUNTAAAAAA!

        System.out.println("Estoy en ELOL");
        switch (sim) {
            case '|':
                avance();
                NoTerminal s2 = new NoTerminal("s2",0,0), s4 = new NoTerminal("s4",0,0);
                EL2(s2);
                pRelacional(i1, s2);
                procOR(i1, s2, s4);
                ELOL(s4, s1);
                break;
            case ')': case'¬':
                s1.setValor(i1.getValor());
                s1.setValorLogico(i1.getValorLogico());
                s1.setRelacional(i1.getRelacional());
                break;
            default: 
                System.out.println("Secuencia"+cad+" no se acepta en ELOL");
                rechace();
        }
        
    }

    public static void EL2(NoTerminal s1){

        System.out.println("Estoy en EL2");
        switch (sim) {
            case 'i':case '(':
                NoTerminal s2 = new NoTerminal("s2",0,0);
                ER(s2);// 3
                EL2L(s2, s1);
                break;
            default: 
                    System.out.println("Secuencia"+cad+" no se acepta en EL2");
                    rechace();
        }
    }

    public static void EL2L(NoTerminal i1, NoTerminal s1){ //Profe que putas
        
        System.out.println("Estoy en EL2L");
        switch (sim) {
            case '&':
                avance();
                NoTerminal s2 = new NoTerminal("s2",0,0), s4 = new NoTerminal("s4",0,0);
                ER(s2);
                pRelacional(i1, s2);
                procAND(i1, s2, s4);
                EL2L(s4, s1);
                break;
            case '|':case ')':case '¬':
                s1.setValor(i1.getValor());
                s1.setValorLogico(i1.getValorLogico());
                s1.setRelacional(i1.getRelacional());
                break;
            default: 
                    System.out.println("Secuencia"+cad+" no se acepta en EL2L");
                    rechace();
        }
    }
    public static void ER(NoTerminal s1){
        
        System.out.println("Estoy en ER");
        switch (sim) {
            case 'i':case '(':
                NoTerminal s2 = new NoTerminal("s2",0,0);
                E(s2);// 4
                ERL(s2, s1);
                break;
            default: 
                    System.out.println("Secuencia"+cad+" no se acepta en ER");
                    rechace();
        }
    }
    public static void ERL(NoTerminal i1, NoTerminal s1){ // CHECK
        System.out.println("Estoy en ERL");
        switch (sim) {
            case '>':case '<':case '=':case '!':
                NoTerminal s2 = new NoTerminal("s2",0,0), s3 = new NoTerminal("s3",0,0);
                OR(s2);
                E(s3);
                pComparar(i1 ,s3, s2, s1);
                break;
            case '|':case '&':case ')':case '¬':
                s1.setValor(i1.getValor());
                s1.setValorLogico(i1.getValorLogico());
                s1.setRelacional(i1.getRelacional());
                break;
            default: 
                    System.out.println("Secuencia"+cad+" no se acepta en ERL");
                    rechace();
        }
    }
    public static void OR(NoTerminal s1){ // CHECK
        System.out.println("Estoy en OR");
        switch (sim) {
            case '>':
                avance();
                MA(s1);
                break;
            case '<':
                avance();
                ME(s1);
                break;
            case '=':
                avance();
                IG(s1);
                break;
            case '!':
                avance();
                DI(s1);
                break;
            default:
                    System.out.println("Secuencia"+cad+" no se acepta en OR");
                    rechace();
        }
    }

    public static void MA(NoTerminal s1){
        System.out.println("Estoy en MA");
        switch (sim) {
            case '=':
                avance();
                s1.setValor(3);
                break;
            case 'i':case '(':
                s1.setValor(4);
                break;
            default: 
                    System.out.println("Secuencia"+cad+" no se acepta en MA");
                    rechace();
        }
    }

    public static void E(NoTerminal s1){ // CHECK
        System.out.println("Estoy en E");
        switch (sim) {
            case 'i':case '(':
                NoTerminal s2 = new NoTerminal("s2",0,0);
                T(s2); // 5
                EL(s2, s1); // 6
                break;
            default: 
                    System.out.println("Secuencia"+cad+" no se acepta en E");
                    rechace();
        }
    }
    
    public static void ME(NoTerminal s1){
        System.out.println("Estoy en ME");
        switch (sim) {
            case 'i':case '(':
                s1.setValor(2);
                break;
            case '=':
                avance();
                s1.setValor(1);
                break;
            default: 
                    System.out.println("Secuencia"+cad+" no se acepta en ME");
                    rechace();
        }
    }
    public static void IG(NoTerminal s1){
        System.out.println("Estoy en IG");
        switch (sim) {
            case '=':
                avance();
                s1.setValor(5);
                break;
            default: 
                    System.out.println("Secuencia"+cad+" no se acepta en IG");
                    rechace();
        }
    }
    public static void DI(NoTerminal s1){
        System.out.println("Estoy en DI");
        switch (sim) {
            case '=':
                avance();
                s1.setValor(6);
                break;
            default: 
                    System.out.println("Secuencia"+cad+" no se acepta en DI");
                    rechace();
        }
    }
    public static void EL(NoTerminal i1, NoTerminal s1){
        System.out.println("Estoy en EL");
        NoTerminal s2 = new NoTerminal("s2",0,0), s3 = new NoTerminal("s3",0,0);
        switch (sim) {
            case '+':
                avance();
                T(s2); // 9
                suma(i1, s2, s3);
                EL(s3, s1);
                break;
            case '-':
                avance();
                T(s2);
                resta(i1, s2, s3);
                EL(s3, s1);
                break;
            case '>':case '<':case '=':case '!':case '|':case '&':case ')':case '¬':
                s1.setValor(i1.getValor());
                s1.setValorLogico(i1.getValorLogico());
                s1.setRelacional(i1.getRelacional());
                break;
            default: 
                    System.out.println("Secuencia"+cad+" no se acepta en EL");
                    rechace();
        }
    }
    public static void T(NoTerminal s1){
        System.out.println("Estoy en T");
        System.out.println(sim);
        switch (sim) {
            case 'i':case '(':
                NoTerminal s2 = new NoTerminal("s2",0,0);
                P(s2); // 6 - 1 listo vemos +
                TL(s2, s1); // 8
                break;
            default: 
                    System.out.println("Secuencia"+cad+" no se acepta en T");
                    rechace();
        }
    }
    public static void TL(NoTerminal i1, NoTerminal s1){
        System.out.println("Estoy en TL");
        NoTerminal s2 = new NoTerminal("s2",0,0), s3 = new NoTerminal("s3",0,0);
        switch (sim) {
            case '*':
                avance();
                P(s2);
                mult(i1, s2, s3);
                TL(s3, s1);
                break;
            case '/':
                avance();
                P(s2);
                div(i1, s2, s3);
                TL(s3, s1);
                break;
            case '+':case '-':case '>':case '<':case '=':case '!':case '|':case '&':case ')':case '¬':
                s1.setValor(i1.getValor());
                s1.setValorLogico(i1.getValorLogico());
                s1.setRelacional(i1.getRelacional());
                break;
            default: 
                    System.out.println("Secuencia"+cad+" no se acepta TL");
                    rechace();
        }
    }
    public static void P(NoTerminal s1){ // FALTA
        System.out.println("Estoy en P");
        switch (sim) {
            case 'i':case '(':
                NoTerminal s2 = new NoTerminal("s2",0,0);
                F(s2); // 7 - 1 listo
                PL(s2, s1); // 8
                break;
            default: 
                    System.out.println("Secuencia"+cad+" no se acepta P");
                    rechace();
        }
    }
    public static void PL(NoTerminal i1, NoTerminal s1){ // ES P-L EN LA TABLA
        System.out.println("Estoy en PL");
        switch (sim) {
            case '^':
                avance();
                NoTerminal s2 = new NoTerminal("s2",0,0), s3 = new NoTerminal("s3",0,0);
                F(s2);
                exp(i1, s2, s3);
                PL(s3, s1);
                break;
            case '+':case '-':case '*':case '/':case '>':case '<':case '=':case '!':case '|':case '&':case ')':case '¬':
                s1.setValor(i1.getValor());
                s1.setValorLogico(i1.getValorLogico());
                s1.setRelacional(i1.getRelacional());
                break;
            default: 
                    System.out.println("Secuencia"+cad+" no se acepta PL");
                    rechace();
        }
    }
    public static void F(NoTerminal s1){ // FALTA
        System.out.println("Estoy en F");
        Elemento elemento;
        switch (sim) {
            case 'i':
                elemento = lex1.darElemento(indice);
                s1.setValor(elemento.darValor());
                avance();
                break;
            case '(':
                avance();
                ELO(s1);
                System.out.println("F :" + sim);
                elemento = lex1.darElemento(indice);
                if (elemento.darTipo() != ')') rechace();
                avance(); // ?
                break;
            default: 
                System.out.println("Secuencia"+cad+" no se acepta F");
                rechace();
        }
        
    }
    public static void pRelacional(NoTerminal i1,NoTerminal i2){
        if (i1.getRelacional())
            if (i2.getRelacional())
                return;
            else {
                System.out.println("Debe ser el resultado de una expresión relacional 2");
                System.out.println("Secuencia"+cad+" no se acepta");
                rechace();
            }
        else {
            System.out.println("Debe ser el resultado de una expresión relacional  1");
            System.out.println("Secuencia"+cad+" no se acepta");
            rechace();
        }
    }

public static void pComparar(NoTerminal i1, NoTerminal i2, NoTerminal s2, NoTerminal s1){

        switch ((int)s2.getValor()){
            case 1: if(i1.getValor()<=i2.getValor()) s1.setValorLogico(true);
                    else s1.setValorLogico(false);
                    break;               
            case 2: if(i1.getValor()<i2.getValor()) s1.setValorLogico(true);
                    else s1.setValorLogico(false);
                    break;
            case 3: if(i1.getValor()>=i2.getValor()) s1.setValorLogico(true);
                    else s1.setValorLogico(false);
                    break;
            case 4: if(i1.getValor()>i2.getValor()) s1.setValorLogico(true);
                    else s1.setValorLogico(false);
                    break;
            case 5: if(i1.getValor()==i2.getValor()) s1.setValorLogico(true);
                    else s1.setValorLogico(false);
                    break;
            case 6: if(i1.getValor()!=i2.getValor()) s1.setValorLogico(true);
                    else s1.setValorLogico(false);
        }
        s1.setRelacional(true);
    }

    public static void procOR(NoTerminal i1, NoTerminal i2, NoTerminal nt){ // TODO OR en que sentido?
        nt.setValorLogico(i1.getValorLogico() || i2.getValorLogico());
    }
    public static void procAND(NoTerminal i1, NoTerminal i2, NoTerminal nt){ // TODO en que sentido es el and?
        nt.setValorLogico(i1.getValorLogico() && i2.getValorLogico());
    }
    public static void suma(NoTerminal nt1, NoTerminal nt2, NoTerminal nt){
        nt.setValor(nt1.getValor()+nt2.getValor());
    }
    public static void resta(NoTerminal nt1, NoTerminal nt2, NoTerminal nt){
        nt.setValor(nt1.getValor()-nt2.getValor());
    }
    public static void mult(NoTerminal nt1, NoTerminal nt2, NoTerminal nt){
        nt.setValor(nt1.getValor()*nt2.getValor());
    }
    public static void div(NoTerminal nt1, NoTerminal nt2, NoTerminal nt){
        nt.setValor(nt1.getValor()/nt2.getValor());
    }
    public static void exp(NoTerminal nt1, NoTerminal nt2, NoTerminal nt){
        nt.setValor(Math.pow(nt1.getValor(),nt2.getValor()));
    }

    public static void resultado(NoTerminal res){
        if(res.getRelacional()) System.out.println("Resultado = "+res.getValorLogico());
        else System.out.println("Resultado = "+res.getValor());
    }

        public static void analisisLexico(){
        // Este analizador es sencillo determina solo constantes enteras y reales positivas
        // Trabaja los diferentes elementos en un ArrayList que trabaja con la clase Clexico
        // la cual define el ArrayList con la clase CElemento
        // Almacen los valores para poder hallar los resultados
        
        Elemento ele1; 
        
        int i=0;
        int ind=0;
        char tip=0;
        char sim1=cad.charAt(i);
        double val=0;
        
        while (sim1!='¬'){
            // determina si sim1 esta en la cadena de digitos cad1 que es global
            if (cad1.indexOf(sim1)!=-1){
                String num="";
                while(cad1.indexOf(sim1)!=-1){
                    num=num+sim1;
                    i++;
                    sim1=cad.charAt(i);
        
                }
                // en el String num se almacena el entero y se lo almacena en val como doble
                // DeterminarNumero(num);
                if (determinarNumero(num)){
                    val=Double.parseDouble(num);
                    tip='i';
                
                    // se tipifica el valor como i
                }
                else{
                    System.out.println("Se rechaza la secuencia");
                    System.exit(0);
                }
        
            }
            else {
               // si el simbolo de entrada no esta en cad1 lo tipifica como tal ej
               // +,-,* (,) etc.
                
               tip=(char)sim1;
               i++;
               sim1=cad.charAt(i);
               val=0;
              
            }
        
            // con los elementos establecidos anteriormente se crea el elemento y se lo
            // adicina a lex1 que es el objeto de la clase Clexico
            if (tip!=' '){
            ele1=new Elemento(tip,val,ind);
            lex1.adicionarElemento(ele1);
            
            ind=ind+1;
            }
            //System.out.print("indice ="+ind);
               
        }
        ele1=new Elemento('¬',0,ind);
        lex1.adicionarElemento(ele1);
        lex1.mostrarLexico();
        System.out.println(" cadena"+lex1.cadenaLexico());
    }
    
    public static boolean determinarNumero(String numero){
     // Este método recibe un número en string y determina mediante un autómata finito
     // si está o no correcto. El string es una cadena de dígitos y el punto.
     // Retorna un valor booleano.
     
        int estado=1,i=0;
        char simbolo;
        boolean b=true;
        while (i<numero.length()&&b) {
            simbolo = numero.charAt(i);
            switch (simbolo) {
                case '0':case '1':case '2':case '3':case '4':case '5':case '6':  
                case '7':case '8':case '9':    
                    switch (estado) {
                        case 1:
                           estado=2;
                           i++;
        
                           break;
                        case 2:
                           estado=2;
                           i++;
        
                           break;
                        case 3:
                           estado=4;
                           i++;
        
                           break;
                        case 4:
                            estado=4;
                           i++;
        
                           break;
                    
                    }
                    break;
                case '.':    
                    switch (estado) {
                        case 1:case 3: case 4:
                           b=false;
                           break;
                        case 2:
                           estado=3;
                           i++;
        
                           break;
                        
                    
                    }
                    break;
                default: b=false;
            }
        
    }
        return b;
    }

    public static void avance(){
           indice++;
       if (indice<cad.length()) {
            sim=lex1.darElemento(indice).darTipo();
            cadavance=cadavance+sim;
            System.out.println("Cadena procesada "+cadavance);
       }
    }
    
    public static void mostrarContador(double i2){ // TODO: DOUBLE
    System.out.println("Cantidad de unos "+i2);
    }
    
    public static void rechace(){
        System.out.println("Se rechaza la secuencia");
        System.exit(0);
    }
    
}