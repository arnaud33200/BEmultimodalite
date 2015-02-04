package fr.irit.liihs.m2proihm.curseur;

import java.util.Enumeration;
import javax.media.j3d.Behavior;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.WakeupOnElapsedTime;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;


/*******************************/
/* Classe IntersectionBehavior */
/*******************************/
/**
 *
 *
 * @author  <a href="mailto:berro@univ-tlse1.fr">Alain Berro</a>
 * @version 9 février 2005
 */
public class IntersectionBehavior extends Behavior
{
    /*---------*/
    /* Données */
    /*---------*/

    /**
	 * Condition d'activation.
     */
    private WakeupOnElapsedTime condition = new WakeupOnElapsedTime(20);


    /**
	 * Référence sur le TransformGroup qui va subir la modification.
     */
    private TransformGroup tgCyl;
    private TransformGroup tgCone;
    private TransformGroup tgSphere;

    private Curseur3d curseur;


    /*--------------*/
    /* Constructeur */
    /*--------------*/

    public IntersectionBehavior (TransformGroup tgCyl,
    							 TransformGroup tgCone,
    							 TransformGroup tgSphere,
    							 Curseur3d curseur)
        {
        this.tgCyl = tgCyl;
        this.tgCone = tgCone;
        this.tgSphere = tgSphere;

        this.curseur = curseur;
        }


    /*------------------------------------------------*/
    /* Définition des méthodes abstraites de Behavior */
    /*------------------------------------------------*/

    /**
	 * Initialisation du type de comportement.
     */
    public void initialize ()
        {
        this.wakeupOn(this.condition);
        this.setSchedulingBounds(new BoundingSphere(new Point3d(),10.0));
        }


    /**
	 * Réponse à l'évènement.
     */
    public void processStimulus (Enumeration criteria)
        {
        boolean ouvert = true;

		/*----- Position du curseur -----*/
        Vector3d posCurseur = this.curseur.getPosition();

		/*----- Position du cylindre -----*/
		Transform3D t3d = new Transform3D();
		Vector3d v3d1 = new Vector3d();
        this.tgCyl.getTransform(t3d);
        t3d.get(v3d1);

		if	(distance(v3d1,posCurseur) < 1.75) ouvert = false;

		/*----- Position du cône -----*/
        this.tgCone.getTransform(t3d);
        t3d.get(v3d1);

		if	(distance(v3d1,posCurseur) < 1.75) ouvert = false;

		/*----- Position de la sphère -----*/
        this.tgSphere.getTransform(t3d);
        t3d.get(v3d1);

		if	(distance(v3d1,posCurseur) < 1.75) ouvert = false;

		this.curseur.setOuvert(ouvert);

        this.wakeupOn(this.condition);
        }


	/**
	 * Calcule la distance entre deux points de l'espace.
	 */
	private static double distance (Vector3d v0,
									Vector3d v1)
		{
		return Math.sqrt((v0.x-v1.x)*(v0.x-v1.x) + (v0.y-v1.y)*(v0.y-v1.y) + (v0.z-v1.z)*(v0.z-v1.z));
		}

} /*----- Fin de la classe IntersectionBehavior -----*/