#include<iostream>

class Animal {
private:
	std::string name;
	double height;
	double weight;
	static int numberOfAnimals;

public:
	std::string GetName() {return name;}
	void SetName(std::string name) {this->name.assign(name);}
	double getHeight() {return this->height;}
	void setHeight(double height) {this->height = height;}
	double getWeight() {return this->weight;}
	void setWeight(double weight);
	static int getNumberOfAnimals() {return numberOfAnimals;}
	void toString();
	void setAnimals(std::string name, double height, double weight);
    void setAnimals(std::string name, double weight);
	Animal();
	~Animal();
};
//
//void Animal::setWeight(double weight){this->weight = weight;}
//
//int Animal::numberOfAnimals = 0;
//
//void Animal::setAnimals(std::string name, double weight) {
//	this->name = name;
//	this->weight = weight;
//	Animal::numberOfAnimals++;
//}
//
//void Animal::setAnimals(std::string name, double height, double weight) {
//	this->name = name;
//	this->weight = weight;
//	this->height = height;
//	Animal::numberOfAnimals++;
//}
//Animal::Animal() {
//	this->name.assign("");
//	this->weight = 0;
//	this->height = 0;
//}
//
//Animal::~Animal() {
//	std::cout << "Animal " << this->name << "destroyed\n";
//}
//
//void Animal::toString() {
//	std::cout<< " name: " << this->name << " height: " << this->height <<
//		" weight: " << this->weight << "\n";
//}

