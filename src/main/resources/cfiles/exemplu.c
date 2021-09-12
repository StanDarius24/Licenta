int power(int b, int e)
{
    if(e == 0)
        return 1;

    return (b*power(b, e-1));
}

void print(struct Person *p) {
	while(p!=NULL) {
		printf("name:%s,  age:%d, street:%s\n",p->name, p->age, p->street);
		p=p->urm;
	}
}