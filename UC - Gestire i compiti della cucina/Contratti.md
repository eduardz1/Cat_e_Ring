# Contratti per lo UC "Gestire i compiti in cucina"

**Pre-condizione generale**: l'utente è identificato come Chef

## 1.  apriFoglio(*servizio*: Servizio)
### Pre-condizioni:
### Post-condizioni:
- [**Se esiste** già un foglio associato al servizio ]
	- viene aperto # **Un po' tanto vago**
-  [**Altrimenti**]
	- crea un'istanza *f* di FoglioRiepilogativo
	- f->pubblicato = no
	- visualizza a schermo i dettagli dell'oggetto *servizio*

## 2. apriTabelloneTurni()
### Pre-condizioni:
- È in corso la modifica di un FoglioRiepilogativo f

### Post-condizioni:
- Viene visualizzato il tabellone dei turni

## 3. InserisciVoce(*voce?*: Voce, *sezione?*: Sezione)
### Pre-condizioni:
- È in corso la modifica di un FoglioRiepilogativo f

### Post-condizioni:
-  [**Se** *sezione* è specificato] f->servizio += *sezione*
-  [**Se** *voce* è specificato] f->servizio->sezione[^1] += *voce*

## 4.1 nuovoCompito(*mansioneDiCucina*: Mansione, *cuocoAssegnato*: Cuoco)
### Pre-condizioni:
- È in corso la modifica di un foglio riepilogativo f

### Post-condizioni:
- è stata creata un'istanza *c* di Compito che **contiene** *mansioneDiCucina*
- c->completato = no;
- c->porzione = //
- c->quantità = //
- c->stimaTempo = //
- c è associato a un'istanza di Ricetta **o** un'istanza di Preparazione
- c->cuoco = cuocoAssegnato
- f **contiene** c

## 4.2 inserisciInfoCompito(*compito*: Compito, *quantità?*: Testo, *porzione?*: Testo, *stimaTempo?*: Testo)
### Pre-condizioni:
- È in corso la modifica di un foglio riepilogativo f
- *compito* è **contenuto** in *f*

### Post-condizioni:
- compito->porzione = porzione
- compito->quantità = quantià
- compito->stimaTempo = stimaTempo

## 4a.1 cambiaOrdineCompito(*compito*: Compito)
### Pre-condizioni:
- È in corso la modifica di un foglio riepilogativo f

### Post-condizioni:
- L'ordine della lista di istanze di Compito contenute in *f* è aggiornato

## 4b.1 annotaCuocoNonNecessario(*compito*: Compito)
### Pre-condizioni:
- È in corso la modifica di un foglio riepilogativo f
- *compito* è **contenuto** in *f*

### Post-condizioni:
- compito->cuoco = NON_NECESSARIO

## 4c.1 eliminaInfoCompito(*compito*: Compito, *quantità?*: Bool, *porzione?*: Bool, *stimaTempo?*: Bool, *cuoco?*: Bool)
### Pre-condizioni:
- È in corso la modifica di un foglio riepilogativo f
- compito è **contenuto** in *f*

### Post-condizioni:
- [**Se** *quantità*] compito->quantità = //
- [**Se** *quantità*] compito->porzione = //
- [**Se** *quantità*] compito->stimaTempo = //
- [**Se** *quantità*] compito->cuoco = //

## 4d.1 eliminaCompito(*compito*: Compito)
### Pre-condizioni:
- È in corso la modifica di un foglio riepilogativo f

### Post-condizioni:
- *compito* viene rimosso da *f*

## 4e.1 resettaFoglio()
### Pre-condizioni:
- È in corso la modifica di un foglio riepilogativo f

### Post-condizioni:
- ogni istanza di Compito contenuta in *f* viene eliminata

## 5 pubblicaFoglio()
### Pre-condizioni:
- È in corso la modifica di un foglio riepilogativo f

### Post-condizioni:
- f->pubblicato = sì

[^1]: di default potrebbe esserci una sezione con nome vuoto in maniera tale che le voci possano essere anche inserite fuori da una sezione senza gestire casi separati