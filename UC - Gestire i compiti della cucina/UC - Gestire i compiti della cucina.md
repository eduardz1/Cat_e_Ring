# Gestire i Compiti della Cucina

## Informazioni generali
**Nome caso d'uso**: Gestire i compiti della cucina
**Portata**: Sistema
**Livello**: Obiettivo utente
**Attore primario**: Chef
**Parti interessate**: Cuoco
**Pre-condizioni**: L'attore deve essere autenticato come Chef
**Garanzie di successo o post-condizioni**: I compiti sono visualizzabili come tabelle organizzate per turni o servizio

## Scenario principale di successo

| **#** | **Attore** | **Sistema** |
|:------|:-----------|:------------|
| 1 | Apre un foglio per un determinato servizio a partire da un servizio esistente | Registra il foglio creato a partire da un menu pubblicato |
| |  *Se desidera passa al punto 2 altrimenti termina il caso d'uso* | |
| 2 | *Opzionalmente* consulta il tabellone dei turni | |
| 3 | *Opzionalmente* inserisce voci non presenti nel menu | |
| 4 | *Opzionalmente* <ul><li>assegna cuochi a compiti</li><li>assegna quantità e porzioni di un piatto</li><li>inserisce una stima dei tempi di preparazione di un piatto</li><ul> | Registra le informazioni inserite dallo chef |
| | *Ripete 2 finché non soddisfatto* | |

### Estensione 4a
| **#** | **Attore** | **Sistema** |
|:------|:-------|:---------|
| 4a.1 | Cambia l'ordine degli incarichi | Registra il nuovo ordine degli incarichi |

### Estensione 4b
| **#** | **Attore** | **Sistema** |
|:------|:-------|:---------|
| 4b.1 | Annota il fatto che per certe preparazioni non è necessario uno chef asseganto | Modifica l'assegnazione dello chef a determinate preaprazioni, un'assegnazione vuota viene distinta da un'assegnazione volutamente omessa |

### Estensione 4c
| **#** | **Attore** | **Sistema** |
|:------|:-------|:---------|
| 4c.1 | Elimina un'informazione dal foglio organizzativo | Registra l'eliminazione dell'informazione |

### Estensione 4d
| **#** | **Attore** | **Sistema** |
|:------|:-------|:---------|
| 4d.1 | Resetta il foglio organizzativo | Ripristina tutti i campi del foglio |

### Eccezione 4c.a
| **#** | **Attore** | **Sistema** |
|:------|:-------|:---------|
| 4c.a.1 | Il compito relativo è già sttao completato | |

### Eccezione 4c.b
| **#** | **Attore** | **Sistema** |
|:------|:-------|:---------|
| 4c.b.1 | L'utente non ha il permesso di effettuare modifiche ||

### Eccezione 4d.a
| **#** | **Attore** | **Sistema** |
|:------|:-------|:---------|
| 4d.a.1 | L'utente non ha il permesso di effettuare modifiche ||
