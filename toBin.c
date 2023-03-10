#include <stdio.h>
#include <string.h>

#define S 10 // tamano do opcode

void decimalToBinary(int dec, char * bin){
  int i = 0;
  while (dec > 0){
    bin[i++] = (dec % 2) + '0'; // converte para char
    dec = dec / 2;
  }
}

void invert(char * bin){
  char aux; int i,j;
  int size = strlen(bin);
  for(i = 0,j = size-1; i < (size/2); i++ , j--){
    aux = bin[i];
    bin[i] = bin[j];
    bin[j] = aux;
  }
}

int main(){
  int decimal;
  char binary[S];
  printf("Enter with a decimal number: ");
  scanf("%d", &decimal);
  decimalToBinary(decimal,binary);
  invert(binary);
  printf("Binary number = %s\n", binary);
  return 0;
}