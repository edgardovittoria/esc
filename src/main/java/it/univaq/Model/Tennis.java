package it.univaq.Model;

import java.util.ArrayList;

public class Tennis extends Sport{

	private Tennis tennisInstance;

	private Tennis(){

	}		

	public static Tennis getInstance(){
		if (this.tennisInstance == null){
			this.tennisInstance = new Tennis();
		}

		return this.tennisInstance;
	}
}
