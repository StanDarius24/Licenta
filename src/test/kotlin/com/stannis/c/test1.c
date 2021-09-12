#include <stdio.h>
#include <stdlib.h>
#include <string.h>

typedef struct Person{
	char name[20];
	int age;
	char street[20];
	struct Person *urm;
}Person;

Person *createPerson(char *name, int age, char *street) {
	struct Person *p = malloc(sizeof(Person));
	strcpy(p->name, name);
	p->age = age;
	strcpy(p->street, street);
	p->urm = NULL;
	return p;
}

Person * addToPerson(struct Person *p1, struct Person *p2) {
	return p1->urm = p2;
}

void print(struct Person *p) {
	while(p!=NULL) {
		printf("name:%s,  age:%d, street:%s\n",p->name, p->age, p->street);
		p=p->urm;
	}
}

int main(int argc, char const *argv[])
{

	struct Person *p = createPerson("Darius", 22, "Libertatii");
	struct Person *q = createPerson("Andrei", 23, "Splai");

	addToPerson(p,  q);

	addToPerson(q, createPerson("Vlad", 22, "Revolutiei"));

	print(p);

	return 0;
}