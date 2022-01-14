#define _CRT_SECURE_NO_WARNINGS
#include<iostream>
#include<stdio.h>
#include<string.h>
#include<stdlib.h>
#include<algorithm>
#include<string>
using namespace std;
int cases;
class headquarter;
class warrior {
private:
	headquarter* pH;
	int order;
public:
	static int warrior_blood[5];
	static const char warrior_name[5][10];
	warrior(headquarter* p,int _order) {
		pH = p;
		order = _order;
	}
	void warrior_born(int _time);
};
int warrior::warrior_blood[5];
const char warrior::warrior_name[5][10] = { "dragon" ,"ninja","iceman","lion","wolf" };

class headquarter {
	friend warrior;
private:
	warrior* pW[1000];
	int blood;
	char color[10];
	int warrior_num[5];
	int total_num;
	int index_order;
	int type;
	bool end;
public:
	static int order[2][5];
	void init(int _blood, const char* _color) {
		blood = _blood;
		strcpy(color, _color);
		for (int i = 0; i < 5; ++i)
			warrior_num[i] = 0;
		if (strcmp(color, "red") == 0)
			type = 0;
		else
			type = 1;
		index_order = 0;
		total_num = 0;
		end = false;
	}
	~headquarter() {
		for (int i = 0; i < total_num; ++i)
			delete pW[i];
	}
	bool GetEnd() { return end; }
	void produce(int _time) {
		if (!end) {
			int count = 0;
			int last_order = order[type][index_order];
			while (blood < warrior::warrior_blood[last_order] && count <= 5) {
				count++;
				index_order = (index_order + 1) % 5;
				last_order = order[type][index_order];
			}
			if (count > 5) {
				end = true;
				printf("%03d %s headquarter stops making warriors\n", _time, color);
			}
			else {
				pW[total_num] = new warrior(this, last_order);
				warrior_num[last_order]++;
				pW[total_num]->warrior_born(_time);
				blood -= pW[total_num]->warrior_blood[last_order];
				index_order = (index_order + 1) % 5;
			}
		}
	}
};
int headquarter::order[2][5] = { {2,3,4,1,0}, {3,0,1,2,4} };

void warrior::warrior_born(int _time) {
	printf("%03d %s %s %d born with strength %d,%d %s in %s headquarter\n",
		_time, pH->color, warrior_name[order], _time + 1,
		warrior_blood[order], pH->warrior_num[order],
		warrior_name[order], pH->color);
}

int main() {
	cin >> cases;
	for (int i = 1; i <= cases; ++i) {
		int blood;
		cin >> blood;
		for (int i = 0; i < 5; ++i)
			cin >> warrior::warrior_blood[i];
		headquarter red;
		red.init(blood, "red");
		headquarter blue;
		blue.init(blood, "blue");
		printf("Case:%d\n", i);
		int time = 0;
		while (!red.GetEnd() || !blue.GetEnd()) {
			red.produce(time);
			blue.produce(time);
			time++;
		}
	}
}