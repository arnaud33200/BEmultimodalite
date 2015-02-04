package fr.irit.elipse.m2proihm.curseur;

import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.geometry.Cone;
import com.sun.j3d.utils.geometry.Cylinder;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.universe.SimpleUniverse;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GraphicsConfiguration;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.media.j3d.Appearance;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.Locale;
import javax.media.j3d.Material;
import javax.media.j3d.PhysicalBody;
import javax.media.j3d.PhysicalEnvironment;
import javax.media.j3d.PolygonAttributes;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.View;
import javax.media.j3d.ViewPlatform;
import javax.media.j3d.VirtualUniverse;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

import jp.nyatla.nyartoolkit.NyARException;
import jp.nyatla.nyartoolkit.core.NyARCode;




/***********************/
/* Classe AppCurseur3d */
/***********************/
/**
 * Curseur 3d.
 */
public class AppCurseur3d extends JFrame 
{


	/**
	 * Données relatives à la fenêtre.
	 */
	private int largeur;	// Taille
	private int hauteur;
	private int posx;		// Position
	private int posy;


	/**
	 * Objets composants la structure principale.
	 */
	private VirtualUniverse	universe;
	private Locale			locale;
	private View			view;

	private BranchGroup		racineVue;		// Noeud de branchement de la vue
	private BranchGroup		racineVolume;	// Noeud de branchement du volume

	private TransformGroup	tgVue;			// Noeud de transformation attaché au point de vue
	private TransformGroup	tgVolume;		// Noeud de transformation attaché au volume

	private BoundingSphere	bounds = new BoundingSphere(new Point3d(0.0,0.0,0.0),100.0);

	private Curseur3d		curseur;

	public Curseur3d getCurseur() {
		return curseur;
	}


	public void setCurseur(Curseur3d curseur) {
		this.curseur = curseur;
	}

	private CurseurKeyListener	ckl;


	/*--------------*/
	/* Constructeur */
	/*--------------*/

	public AppCurseur3d (int l,
			int h,
			int px,
			int py)
	{
		/*----- Instanciation de la fenêtre graphique -----*/
		this.setTitle("Curseur 3D");
		this.largeur = l;
		this.hauteur = h;
		this.setSize(largeur,hauteur);
		this.posx = px;
		this.posy = py;
		this.setLocation(posx,posy);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		/*----- Contenu de la fenêtre -----*/
		Container conteneur = getContentPane();
		conteneur.setLayout(new BorderLayout());

		/*----- Création du Canvas -----*/
		GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
		Canvas3D c = new Canvas3D(config);
		conteneur.add("Center",c);

		this.ckl = new CurseurKeyListener();
		c.addKeyListener(ckl);

		/*----- Création d'un bouton de remise à zéro de la position du curseur -----*/
		JButton raz = new JButton("RAZ curseur");
		conteneur.add("South",raz);

		raz.addActionListener(new ActionListener()
		{
			public void actionPerformed (ActionEvent e)
			{
				curseur.initialise();
			}
		});

		/*----- Création de l'univers virtuel -----*/
		this.universe = new VirtualUniverse();
		this.locale = new Locale(this.universe);

		/*----- Création du noeud pour insérer les éléments de vue -----*/
		this.racineVue = new BranchGroup();

		/*----- Position de l'observateur -----*/
		Transform3D t3d_oeil = new Transform3D();
		t3d_oeil.set(new Vector3d(0.0,0.0,10.0));

		this.tgVue = new TransformGroup(t3d_oeil);
		this.racineVue.addChild(this.tgVue);

		/*----- Création de la plateforme de vue et attachement-----*/
		ViewPlatform vp = new ViewPlatform();
		this.tgVue.addChild(vp);

		/*----- Création d'une vue -----*/
		this.view = new View();

		/*----- Création de l'univers physique -----*/
		PhysicalBody body = new PhysicalBody();
		PhysicalEnvironment env = new PhysicalEnvironment();

		/*----- Liaison de la plateforme de vue, du canvas et de l'univers physique à la vue-----*/
		this.view.attachViewPlatform(vp);
		this.view.addCanvas3D(c);
		this.view.setBackClipDistance(100.0);
		this.view.setPhysicalBody(body);
		this.view.setPhysicalEnvironment(env);

		/*----- Création du noeud racine et de la matrice de transformation de la branche volume -----*/
		this.tgVolume = new TransformGroup();
		this.racineVolume = new BranchGroup();

		/*----- Ajout d'un comportement souris -----*/
		this.tgVolume.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		this.tgVolume.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

		MouseRotate mouseRot = new MouseRotate(this.tgVolume);
		mouseRot.setSchedulingBounds(this.bounds);
		this.racineVolume.addChild(mouseRot);

		this.racineVolume.addChild(this.tgVolume);
		this.tgVolume.addChild(createBrancheVolume());

		/*----- Ajout à Locale de Viewplatform + SceneGraph -----*/
		this.locale.addBranchGraph(racineVue);
		this.locale.addBranchGraph(racineVolume);

		/*----- Rend la fenêtre visible -----*/
		this.setVisible(true);
	}


	/*----------*/
	/* Méthodes */
	/*----------*/

	/**
	 * Création du volume.
	 */
	private BranchGroup createBrancheVolume ()
		{
		
		
		/*----- Création du noeud racine -----*/
		BranchGroup racine = new BranchGroup();

		/*----- Création de la source de lumière -----*/
		Color3f lumColor = new Color3f(1.0f,1.0f,1.0f);
		Vector3f lumDir  = new Vector3f(-1.0f,-1.0f,-1.0f);

		DirectionalLight dirlum = new DirectionalLight(lumColor,lumDir);
		dirlum.setInfluencingBounds(this.bounds);
		racine.addChild(dirlum);

		/*----- Création du Volume -----*/

		/*----- Cône -----*/
		Transform3D t3d = new Transform3D();

		TransformGroup tgCone = new TransformGroup();
		tgCone.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		t3d.setTranslation(new Vector3d(3.0,3.0,-3.0));
		tgCone.setTransform(t3d);

		Cone co = new Cone();
		co.setAppearance(this.createAppearance(3));
		tgCone.addChild(co);
		racine.addChild(tgCone);
		

		/*----- Sphère -----*/
		TransformGroup tgSphere = new TransformGroup();
		tgSphere.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		t3d.setTranslation(new Vector3d(-3.0,0.0,0.0));
		tgSphere.setTransform(t3d);

		Sphere sph = new Sphere(1.0f);
		sph.setAppearance(this.createAppearance(1));
		tgSphere.addChild(sph);
		racine.addChild(tgSphere);

		/*----- Cylindre -----*/
		TransformGroup tgCyl = new TransformGroup();
		tgCyl.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		t3d.setTranslation(new Vector3d(0.0,0.0,-3.0));
		tgCyl.setTransform(t3d);

		Cylinder cyl = new Cylinder();
		cyl.setAppearance(this.createAppearance(2));
		tgCyl.addChild(cyl);
		racine.addChild(tgCyl);

		/*----- Curseur -----*/
		this.curseur = new Curseur3d();
		racine.addChild(this.curseur.getNoeudRacine());

		/*----- Ajout de la navigation à l'aide du clavier -----*/
//		KeyNavigatorBehavior key = new KeyNavigatorBehavior(this.curseur.getNoeudRacine());
//		key.setSchedulingBounds(this.bounds);
//		racine.addChild(key);

		CurseurBehavior cb = new CurseurBehavior(this.curseur,this.ckl);
		racine.addChild(cb);

		/*----- Comportement d'intersection -----*/
		IntersectionBehavior ib = new IntersectionBehavior(tgCyl,tgCone,tgSphere,this.curseur);
		racine.addChild(ib);

		/*----- Optimisation du graphe de scène -----*/
		racine.compile();
		return racine;
		}


	/**
	 * Retourne une apparence.
	 */
	public Appearance createAppearance (int numero)
	   {
	   Appearance aspect = new Appearance();

	   switch (numero)
		   {
		   case 1 :
			   {
			   /*----- Polygone, couleur bleu, sans interpolation des normales -----*/
			   PolygonAttributes poly = new PolygonAttributes();
			   poly.setPolygonMode(PolygonAttributes.POLYGON_FILL);
			   aspect.setPolygonAttributes(poly);

			   ColoringAttributes color = new ColoringAttributes();
			   color.setShadeModel(ColoringAttributes.SHADE_FLAT);
			   aspect.setColoringAttributes(color);

			   aspect.setMaterial(new Material(new Color3f(0.2f,0.2f,0.2f),
											   new Color3f(0.0f,0.0f,1.0f),
											   new Color3f(1.0f,1.0f,1.0f),
											   new Color3f(1.0f,1.0f,1.0f),
											   64f));
			   break;
			   }

		   case 2 :
			   {
			   /*----- Polygone, couleur bleu, avec interpolation des normales -----*/
			   PolygonAttributes poly = new PolygonAttributes();
			   poly.setPolygonMode(PolygonAttributes.POLYGON_FILL);
			   aspect.setPolygonAttributes(poly);

			   aspect.setMaterial(new Material(new Color3f(0.2f,0.2f,0.2f),
											   new Color3f(0.0f,0.0f,1.0f),
											   new Color3f(1.0f,1.0f,1.0f),
											   new Color3f(1.0f,1.0f,1.0f),
											   64f));
			   break;
			   }

		   case 3 :
			   {
			   /*----- Polygone, couleur jaune, avec interpolation des normales -----*/
			   PolygonAttributes poly = new PolygonAttributes();
			   poly.setPolygonMode(PolygonAttributes.POLYGON_FILL);
			   aspect.setPolygonAttributes(poly);

			   aspect.setMaterial(new Material(new Color3f(0.2f,0.2f,0.2f),
											   new Color3f(1.0f,1.0f,0.0f),
											   new Color3f(1.0f,1.0f,1.0f),
											   new Color3f(1.0f,1.0f,1.0f),
											   64f));
			   break;
			   }

		   default :
		   }

	   return aspect;
	   }

	public static void main(String[] args)
	{
		new AppCurseur3d(400,400,0,0);
	}

} /*----- Fin de la classe AppCurseur3d -----*/