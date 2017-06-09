package com.grupoprimario.springboot.model;
	
public class User {
	private long id;	
	private String nombre;
	private int edad;

	public User(){}

	public User(long id){
        this.id=id;
    }

	public User(long id,String nombre,int edad){
		this.id=id;
		this.nombre=nombre;
		this.edad=edad;
    }	
	


    public long getId(){return this.id;}
    public void setId(long id){this.id=id;}

    public String getNombre(){return this.nombre;}
    public void setNombre(String nombre){this.nombre=nombre;}

    public int getEdad(){return this.edad;}
    public void setEdad(int edad){this.edad=edad;}
}
