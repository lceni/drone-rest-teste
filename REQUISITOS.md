#Desenvolver serviços REST para controle de um drone.#

O objetivo deste teste é avaliar seu conhecimento e experiência como desenvolvedor Java.
Deve ser utilizado Gradle para gerenciamento das dependências e compilação.
O projeto será executado na última versão do WildFly (10.1).

###Prazo para entrega: 72 horas.###

##Cenário##

Para combater a dengue o Governo está lançando um sistema de drones com câmeras para mapear áreas de risco.  
Cada drone será programado para mapear uma área quadrada de 30x30 metros.  
Estes drones são capazes de se mover apenas para frente ou girar em seu eixo.  
A posição inicial de cada drone é no meio de sua área apontando para o Norte.  
Cada movimento para frente é de 1 metro.  
Desenvolva um sistema que permita aos operadores controlar a posição dos drones.

###As seguintes interfaces devem estar disponíveis:###

POST /drone/api/move  
Move o drone 1 metro para frente

POST /drone/api/left  
Gira o drone em seu eixo para a esquerda

POST /drone/api/right  
Gira o drone em seu eixo para a direita

POST /drone/api/bulk  
Data: ['L', 'L', 'M', 'M', 'M', 'R', 'M']  
Movimenta o drone com comandos em lote.  
L = Gira o drone em seu eixo para a esquerda  
R = Gira o drone em seu eixo para a direita  
M = Move o drone 1 metro para frente

GET /drone/api/status  
Response: {'x': 15, 'y': 15, 'facing': 'N'}  
Retorna a situação do drone sendo:  
x = Posição no eixo X  
y = Posição no eixo Y  
facing = Para onde o drone está apontando  

Qualquer comando que mova o drone para fora de sua área deverá resultar em erro.  
Qualquer comando inválido no serviço em lote deve resultar em erro e cancelar toda a operação.  
Todos as situações de erro devem ser tratadas e uma mensagem deve ser retornada pelo serviço.
