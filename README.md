# 📝 Compilatore per Linguaggio "P" verso JVM

## 📌 Descrizione
Progetto accademico sviluppato per il corso di *Linguaggi Formali e Traduttori* (A.A. 2023/2024) presso l'Università degli Studi di Torino. 
Il progetto consiste nella realizzazione da zero di un traduttore (Lexer, Parser e Generatore di Codice) per un linguaggio di programmazione procedurale personalizzato, denominato linguaggio "P". Il compilatore analizza il codice sorgente (con estensione `.lft`) e genera codice mnemonico assembler. Quest'ultimo viene poi tradotto in bytecode eseguibile dalla Java Virtual Machine (JVM) avvalendosi dell'assembler Jasmin.
Per descrizione più accurata guardare pdf in docs.

## 🛠 Architettura del Compilatore
Il sistema è strutturato in moduli sequenziali scritti interamente in Java:
*   **Analizzatore Lessicale (Lexer):** Legge il file sorgente e genera una sequenza di token validi, ignorando spazi e gestendo correttamente i commenti, sia a singola riga (`//`) che multilinea (`/* ... */`).
*   **Analizzatore Sintattico (Parser):** Un parser a discesa ricorsiva che verifica la correttezza della sequenza di token rispetto a una grammatica LL(1) appositamente costruita per il linguaggio.
*   **Valutazione Semantica e Symbol Table:** Gestione degli identificatori e dei relativi indirizzi di memoria tramite una tabella dei simboli per evitare conflitti di locazione.
*   **Generazione del Codice (CodeGenerator):** Traduce l'albero sintattico in istruzioni mnemoniche compatibili con l'insieme delle istruzioni della JVM (es. `iload`, `iadd`, `Goto`), scrivendo in output un file `.j`.

## 💻 Caratteristiche del Linguaggio "P"
Il linguaggio "P" supporta diverse strutture di controllo e sintassi peculiari gestite dal compilatore:
*   **Notazione Prefissa per la Matematica:** Le espressioni aritmetiche sono valutate con notazione prefissa e supportano argomenti multipli (es. `+(2, 3)` oppure `+ (2, - 7 3)`).
*   **Gestione I/O:** Comandi nativi `read(x)` per l'input da tastiera e `print(x)` per la stampa su terminale.
*   **Assegnazioni multiple:** Permette di assegnare valori multipli in un'unica istruzione (es. `assign [10 to x] [20 to y]`).
*   **Controllo di Flusso:** Supporto per cicli `for` (inclusi quelli con inizializzazione `for (ID := (expr); (bexpr)) do (stat)`) e blocchi condizionali `if ... else ... end`.
*   **Blocchi di Istruzioni:** Sequenze di istruzioni raggruppate tramite parentesi graffe `{ ... }`.

## 🚀 Come eseguire il progetto

### Prerequisiti
*   Java Development Kit (JDK 19) installato.
*   File `jasmin.jar` (Assembler JVM).
*   Version Jasmin 2.4

### Esecuzione
1. **Compila il sorgente Java:**
   ```bash
   javac *.java
