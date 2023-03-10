#include <stdio.h>
#include <string.h>

#define S 50


// Calcular a potência
// Becouse the lib math don't work

int _pow(int num, int e){
  int sum = 1;
  for(int i = 0; i < e; i++)
    sum *= num;
  return sum;
}

int toDecimal(char * bin){
  int lenght = strlen(bin) ;
  int e = 0;
  int result = 0;
  for(int i = lenght-1; i >= 0; i--){
    result += (bin[i] - '0') * _pow(2,e++);

  }

  return result;
}

int  main(){
  char bin[S] ;
  int decimal;
  printf("Enter with a binary number =>> ");
  scanf("%s", bin);
  decimal = toDecimal(bin);
  printf("Decimal number = %d\n", decimal);
  return  0;
}