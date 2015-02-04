package src.fr.irit.elipse.m2proihm.curseur;

import com.sun.j3d.loaders.IncorrectFormatException;
import com.sun.j3d.loaders.ParsingErrorException;
import com.sun.j3d.loaders.Scene;
import com.sun.j3d.loaders.objectfile.ObjectFile;

import java.io.FileNotFoundException;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Matrix4d;
import javax.vecmath.Vector3d;


/********************/
/* Classe Curseur3d */
/********************/
/**
 * La classe <code>Curseur3d</code> ...
 *
 * @author	<a href="mailto:berro@univ-tlse1.fr">Alain Berro</a>
 * @version	10 février 2005
 */
public class Curseur3d
{
	/*---------*/
	/* Données */
	/*---------*/

	private BranchGroup bgOuvert;

	private BranchGroup bgFerme;

	private TransformGroup tgPosition;

	private TransformGroup tgOrientation;

	private boolean ouvert;


	/*--------------*/
	/* Constructeur */
	/*--------------*/

	/**
	 * Construit et initialise.
	 */
	public Curseur3d ()
		{
		/*----- Chargement du curseur (main ouverte) -----*/
		Scene s1 = null;
		ObjectFile loader = new ObjectFile(ObjectFile.RESIZE);

		try {
			s1 = loader.load("src/fr/irit/elipse/m2proihm/curseur/hand1.obj");
			}
			catch (FileNotFoundException e)
				{
				System.err.println(e);
				System.exit(1);
				}
			catch (ParsingErrorException e)
				{
				System.err.println(e);
				System.exit(1);
				}
			catch (IncorrectFormatException e)
				{
				System.err.println(e);
				System.exit(1);
				}

		/*----- Chargement du curseur (main fermée) -----*/
		Scene s2 = null;

		try {
			s2 = loader.load("src/fr/irit/elipse/m2proihm/curseur/hand3.obj");
			}
			catch (FileNotFoundException e)
				{
				System.err.println(e);
				System.exit(1);
				}
			catch (ParsingErrorException e)
				{
				System.err.println(e);
				System.exit(1);
				}
			catch (IncorrectFormatException e)
				{
				System.err.println(e);
				System.exit(1);
				}

		/*----- Orientation du curseur -----*/
		Transform3D t3d = new Transform3D();

		this.tgOrientation = new TransformGroup();
		this.tgOrientation.setCapability(TransformGroup.ALLOW_CHILDREN_EXTEND);
		this.tgOrientation.setCapability(TransformGroup.ALLOW_CHILDREN_WRITE);
		t3d.rotX(Math.PI/2);
		this.tgOrientation.setTransform(t3d);

		/*----- Curseur (main ouverte) -----*/
		this.bgOuvert = new BranchGroup();
		this.bgOuvert.setCapability(BranchGroup.ALLOW_DETACH);
		this.bgOuvert.addChild(s1.getSceneGroup());

		/*----- Curseur (main fermé) -----*/
		this.bgFerme = new BranchGroup();
		this.bgFerme.setCapability(BranchGroup.ALLOW_DETACH);
		this.bgFerme.addChild(s2.getSceneGroup());

		/*----- Position du curseur -----*/
		this.tgPosition = new TransformGroup();
		this.tgPosition.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		this.tgPosition.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

		this.tgOrientation.addChild(this.bgOuvert);
		this.tgPosition.addChild(this.tgOrientation);

		/*----- Etat du curseur -----*/
		this.ouvert = true;
		}


	/*----------*/
	/* Méthodes */
	/*----------*/

	/**
	 * Retourne le noeud (group) racine du curseur.
	 */
	public TransformGroup getNoeudRacine () { return this.tgPosition; }


	/**
	 * Retourne l'état du curseur.
	 */
	public boolean isOuvert () { return this.ouvert; }


	/**
	 * Mise à jour de l'état du curseur.
	 */
	public void setOuvert (boolean etat)
		{
		if	(this.ouvert)
			{
			if	(!etat)
				{
				this.bgOuvert.detach();
				this.tgOrientation.addChild(this.bgFerme);
				this.ouvert = false;
				}
			}
		else
			{
			if	(etat)
				{
				this.bgFerme.detach();
				this.tgOrientation.addChild(this.bgOuvert);
				this.ouvert = true;
				}
			}
		}


	/**
	 * Retourne le vecteur position du curseur.
	 */
	public Vector3d getPosition ()
		{
		Transform3D t3d = new Transform3D();
		Vector3d v3d = new Vector3d();

		this.tgPosition.getTransform(t3d);
		t3d.get(v3d);

		return v3d;
		}


	/**
	 * Réinitialise le position et l'orientation du curseur.
	 */
	public void initialise () { this.tgPosition.setTransform(new Transform3D()); }


	/**
	 * Mise à jour de la position du curseur.
	 */
	public void setPosition (Vector3d v3d)
		{
		Transform3D t3d = new Transform3D();
		this.tgPosition.getTransform(t3d);
		t3d.setTranslation(v3d);
		this.tgPosition.setTransform(t3d);
		}


	/**
	 * Mise à jour de la coordonnée X du curseur.
	 */
	public void setCoordX (double d)
		{
		Transform3D t3d = new Transform3D();
		Matrix4d m4d = new Matrix4d();

		this.tgPosition.getTransform(t3d);
		t3d.get(m4d);
		m4d.m03 = d;
		t3d.set(m4d);
		this.tgPosition.setTransform(t3d);
		}


	/**
	 * Mise à jour de la coordonnée Y du curseur.
	 */
	public void setCoordY (double d)
		{
		Transform3D t3d = new Transform3D();
		Matrix4d m4d = new Matrix4d();

		this.tgPosition.getTransform(t3d);
		t3d.get(m4d);
		m4d.m13 = d;
		t3d.set(m4d);
		this.tgPosition.setTransform(t3d);
		}


	/**
	 * Mise à jour de la coordonnée Z du curseur.
	 */
	public void setCoordZ (double d)
		{
		Transform3D t3d = new Transform3D();
		Matrix4d m4d = new Matrix4d();

		this.tgPosition.getTransform(t3d);
		t3d.get(m4d);
		m4d.m23 = d;
		t3d.set(m4d);
		this.tgPosition.setTransform(t3d);
		}

	
	/**
	 *  Modification pas à pas de la position du curseur
	 */
	public void translateStepByStep (int motion)
	{
		Vector3d v3d = this.getPosition();
		double pas = 0.1;
		
		switch (motion)
		{
		case 00 : // deplact en X > 0
			v3d.x += pas;
			break;
		case 01 : // deplact en X < 0
			v3d.x -= pas;
			break;
		case 10 : // deplact en Y > 0
			v3d.y += pas;
			break;
		case 11 : // deplact en Y < 0
			v3d.y -= pas;
			break;
		case 20 : // deplact en Z > 0
			v3d.z += pas;
			break;				
		case 21 : // deplact en Z < 0
			v3d.z -= pas;
			break;				
		}
		this.setPosition(v3d);
	
	}
} /*----- Fin de la classe Curseur3d -----*/