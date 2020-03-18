# Projecten Workshops II - Dossier Java - G09
> 19 maart 2020 - 09:00

## 1. Teamleden

| Naam              | Github                                                      | Email                                                                           |
| :---------------- | :---------------------------------------------------------- | :------------------------------------------------------------------------------ |
| Sam Brysbaert     | [brysbaertsam](https://github.com/brysbaertsam)             | [sam.brysbaert@student.hogent.be](mailto:sam.brysbaert@student.hogent.be)       |
| Dante De Ruwe     | [dantederuwe-hogent](https://github.com/dantederuwe-hogent) | [dante.deruwe@student.hogent.be](mailto:dante.deruwe@student.hogent.be)         |
| Arne De Schrijver | [ArneDeSchrijver](https://github.com/ArneDeSchrijver)       | [arne.deschrijver@student.hogent.be](mailto:arne.deschrijver@student.hogent.be) |
| Liam Spitaels     | [liamspitaels](https://github.com/liamspitaels)             | [liam.spitaels@student.hogent.be](mailto:liam.spitaels@student.hogent.be)       |


## 2. De applicatie
### 2.1 Huidige versie (demo)

#### De kalenders en bijhorende sessies
Bij het opstarten van de applicatie komt u op het kalender-selectiescherm terecht. Hier kunt u ook kalenders toevoegen en/of wijzigen. 


<a href="https://i.imgur.com/tm2N2pl.png">
<figure class="image">
  <img src="https://i.imgur.com/tm2N2pl.png">
  <figcaption style="color:gray; text-align:center; margin-right:10px;">Het kalenderselectiescherm</figcaption>
</figure>
</a>
<br><br><br>
<a href="https://i.imgur.com/2Q1it2Y.png">
<figure class="image">
  <img src="https://i.imgur.com/2Q1it2Y.png">
  <figcaption style="color:gray; text-align:center; margin-right:10px;">Het toevoegen / wijzigen van een kalender</figcaption>
</figure>
</a>
<br><br><br>

Wanneer u een kalender selecteert, wordt het sessiescherm actief en wordt u hier automatisch naartoe gebracht. Hier kunt u alle sessies zien voor de geselecteerde kalender (en dus het bijhorende academiejaar). Deze kunnen gesorteerd worden in de tabel door op de kolom-headers te klikken.

Aan de linkerkant ziet u van boven naar onder het geselecteerde academiejaar, een knop om gemakkelijk een sessie toe te voegen en velden om de tabel te filteren. 
Rechts kunt u de informatie van de geselecteerde sessie bekijken, wijzigen, of de sessie verwijderen. Hierover later meer.

<a href="https://i.imgur.com/4Yiyiqo.png">
<figure class="image">
  <img src="https://i.imgur.com/4Yiyiqo.png">
  <figcaption style="color:gray; text-align:center; margin-right:10px;">Het sessiescherm</figcaption>
</figure>
</a>
<br><br><br>
<a href="https://im3.ezgif.com/tmp/ezgif-3-200ebaada237.gif">
<figure class="image">
  <img src="https://im3.ezgif.com/tmp/ezgif-3-200ebaada237.gif">
  <figcaption style="color:gray; text-align:center; margin-right:10px;">Het filteren van de sessies</figcaption>
</figure>
</a>
<br><br><br>


Wanneer u op de "Nieuwe Sessie" knop drukt, word het rechterpaneel automatisch vervangen zodat u hier gemakkelijk een nieuwe sessie kunt opstellen, terwijl u nog steeds het overzicht houdt over de reeds bestaande sessies.

Alle velden zijn zoals gevraagd verplicht, enkel de spreker kan leeggelaten worden. 

De velden worden uiteraard ook eerst gevalideerd waar nodig. Zo kan je geen sessies toevoegen die buiten de huidige geselecteerde sessiekalender liggen, geen sessies voor de huidige dag of in het verleden, geen sessies met een duurtijd korter dan 30 minuten, enzovoort. De gebruiker wordt op de hoogte gebracht als hij foutieve sessies invoert en kan zonodig velden aanpassen.

<a href="https://i.imgur.com/3RK9rFJ.png">
<figure class="image">
  <img src="https://i.imgur.com/3RK9rFJ.png">
  <figcaption style="color:gray; text-align:center; margin-right:10px;">Een sessie toevoegen</figcaption>
</figure>
</a>
<br><br><br>

<a href="https://i.imgur.com/8R2KfUQ.png">
<figure class="image">
  <img src="https://i.imgur.com/8R2KfUQ.png">
  <figcaption style="color:gray; text-align:center; margin-right:10px;">Een sessie toevoegen: validatie van de velden</figcaption>
</figure>
</a>
<br><br><br>


<a href="https://i.imgur.com/0UqqCSP.png">
<figure class="image">
  <img  src="https://i.imgur.com/0UqqCSP.png">
  <figcaption style="color:gray; text-align:center; margin-right:10px;">Voorbeelden van validatie van de velden bij toevoegen en wijzigen van een sessie</figcaption>
</figure>
</a>
<br><br><br>


Als u een sessie succesvol hebt toegevoegd verschijnt deze uiteraard in de tabel met sessies. Na selecteren van een sessie kunt u in het rechterpaneel alle informatie lezen. 


<a href="https://i.imgur.com/12VtKQ2.png">
<figure class="image">
  <img src="https://i.imgur.com/12VtKQ2.png">
  <figcaption style="color:gray; text-align:center; margin-right:10px;">De toegevoegde sessie bekijken</figcaption>
</figure>
</a><br><br><br>


Als u deze informatie van de sessie wenst aan te passen; kunt u via de "wijzig" knop dit gemakkelijk doen. Net zoals bij het aanmaken van een sessie gebeurt dit in het rechterpaneel, maar bij wijzigen zijn de velden natuurlijk al ingevuld voor u. Zoals hierboven reeds vermeld wordt bij het wijzigen dezelfde validatie toegepast als bij het toevoegen.


<a href="https://i.imgur.com/J4bH39O.png">
<figure class="image">
  <img src="https://i.imgur.com/J4bH39O.png">
  <figcaption style="color:gray; text-align:center; margin-right:10px;">Een sessie wijzigen</figcaption>
</figure>
</a>
<br><br><br>
Ook verwijderen is mogelijk via een knop onder de sessie-info. U krijgt uiteraard eerst een waarschuwingsdialoog.


<a href="https://i.imgur.com/yJh9ToR.png">
<figure class="image">
  <img src="https://i.imgur.com/yJh9ToR.png">
  <figcaption style="color:gray; text-align:center; margin-right:10px;">Een sessie verwijderen</figcaption>
</figure>
</a>
<br><br><br>

Tot slot zal u via de andere tabbladen de aankondigingen en feedback kunnen bekijken en beheren. Zie hiervoor onze [plannen](#2.2-Plannen).


#### De gebruikers
Op het gebruikersscherm krijgt u - analoog als bij het sessiescherm - een overzicht van alle gebruikers in het systeem. U kunt deze ook toevoegen, wijzigen, verwijderen en de tabel filteren. U kunt echter de ingelogde gebruiker niet verwijderen, hiervoor is een waarschuwingsdialoog voorzien.

<a href="https://i.imgur.com/ISXIiG0.png">
<figure class="image">
  <img src="https://i.imgur.com/ISXIiG0.png">
  <figcaption style="color:gray; text-align:center; margin-right:10px;">Het gebruikersscherm<br></figcaption>
</figure>
</a>
<br><br><br>
<a href="https://i.imgur.com/7vQhgL9.png">
<figure class="image">
  <img src="https://i.imgur.com/7vQhgL9.png">
  <figcaption style="color:gray; text-align:center; margin-right:10px;">Gebruikers filteren<br></figcaption>
</figure>
</a>
<br><br><br>
<a href="https://i.imgur.com/9CCaUwr.png">
<figure class="image">
  <img src="https://i.imgur.com/9CCaUwr.png">
  <figcaption style="color:gray; text-align:center; margin-right:10px;">Een gebruiker toevoegen<br></figcaption>
</figure>
</a>
<br><br><br>
<a href="https://i.imgur.com/HrLGpuS.png">
<figure class="image">
  <img src="https://i.imgur.com/HrLGpuS.png">
  <figcaption style="color:gray; text-align:center; margin-right:10px;">Waarschuwing bij het verwijderen van een gebruiker<br></figcaption>
</figure>
</a>
<br><br>

### 2.2 Plannen
We zouden op z'n minst graag de functionaliteiten rond aankondigingen en statistieken nog willen implementeren. Ook de feedback behoort nog tot de mogelijkheden, al is de tijd hiervoor gelimiteerd. Ook voor media beheren is de deadline volgens ons te kortbij.


## 3. Vragen voor de klant
1. Heeft u een naam voor de applicatie in gedachten?
2. Hoe verloopt het inloggen op de applicatie?
3. Moet de ingelogde gebruiker zijn accountgegevens kunnen aanpassen?
4. Verdwijnen openstaande sessies uit de desktop appplicatie, of moeten ze  nog kunnen worden gewijzigd?
5. Welke statistieken zijn essentieel?
6. Welke andere feedback kunt u ons nog geven?



## 4. Klassendiagram
<a href="https://i.imgur.com/ry9H6Bw.png">
<figure class="image">
  <img src="https://i.imgur.com/ry9H6Bw.png">
  <figcaption style="color:gray; text-align:center; margin-right:10px;">Het klassendiagram<br></figcaption>
</figure>
</a>
<br><br><br>
