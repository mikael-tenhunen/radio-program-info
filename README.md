# radio-program-info
En Spring Boot-applikation som hämtar information från SR om senast sända avsnitt utifrån programnamn. 

## Instruktioner för att exekvera lokalt
<code>mvn spring-boot:run</code>

## Begränsning av antal samtidiga anrop
Antalet samtidiga anrop begränsas med resilience4j:s bulkhead-mekanism. Att bulkheaden säger ifrån kan verifieras genom att lasta applikationen och verifiera följande loggrad: 

> Bulkhead 'srClient' is full and does not permit further calls

Last kan genereras med exempelvis ab (Apache HTTP server benchmarking tool):

<code>ab -c 10  -n 10 localhost:8080/radio-program-info/latest-episode?programName=Ljudv%C3%A4rld</code>

## Cachad data
Eftersom SR:s API enbart erbjuder uppslag av program och avsnitt utifrån programid och inte utifrån programnamn, hämtas en lista av alla program för att översätta från programnamn till id. Översättningarna cachas i ett dygn.
