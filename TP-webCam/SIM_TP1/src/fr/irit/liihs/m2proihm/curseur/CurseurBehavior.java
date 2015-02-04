package fr.irit.liihs.m2proihm.curseur;

import java.util.Enumeration;
import javax.media.j3d.Behavior;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.WakeupOnElapsedTime;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;


/**************************/
/* Classe CurseurBehavior */
/**************************/
/**
 *
 *
 * @author  <a href="mailto:berro@univ-tlse1.fr">Alain Berro</a>
 * @version 9 février 2005
 */
public class CurseurBehavior extends Behavior
{
    /*---------*/
    /* Données */
    /*---------*/

    /**
	 * Condition d'activation.
     */
    private WakeupOnElapsedTime condition = new WakeupOnElapsedTime(20);

    private Curseur3d curseur;

    private CurseurKeyListener ckl;

	private double pas;


    /*--------------*/
    /* Constructeur */
    /*--------------*/

    public CurseurBehavior (Curseur3d curseur,
    						CurseurKeyListener ckl)
        {
        this.curseur = curseur;
        this.ckl = ckl;

        this.pas = 0.1;
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
	 * Réponse à l'évènement : increment d'un pas vers la droite si RightKey event received
     */
    public void processStimulus (Enumeration criteria)
        {
        Vector3d v3d = this.curseur.getPosition();
        // partiellement remplacable par l'utilisation multiple de 
        // la methode translateStepByStep de Curseru3D
        
        if	(this.ckl.allezADroite())	v3d.x += this.pas;
        if	(this.ckl.allezAGauche())	v3d.x -= this.pas;
        if	(this.ckl.allezEnHaut())	v3d.y += this.pas;
        if	(this.ckl.allezEnBas())		v3d.y -= this.pas;
        if	(this.ckl.allezEnAvant())	v3d.z += this.pas;
		if	(this.ckl.allezEnArriere())	v3d.z -= this.pas;

		this.curseur.setPosition(v3d);

        this.wakeupOn(this.condition);
        }

} /*----- Fin de la classe CurseurBehavior -----*/