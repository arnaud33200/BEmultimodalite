/* 
 * PROJECT: NyARToolkit Java3d sample program.
 * --------------------------------------------------------------------------------
 * The MIT License
 * Copyright (c) 2008 nyatla
 * airmail(at)ebony.plala.or.jp
 * http://nyatla.jp/nyartoolkit/
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * 
 */
//package jp.nyatla.nyartoolkit.java3d.sample;

import java.awt.BorderLayout;
import javax.media.j3d.*;

import com.sun.j3d.utils.universe.*;
import java.awt.*;
import javax.swing.JFrame;
import javax.vecmath.*;

import jp.nyatla.nyartoolkit.core.*;
import jp.nyatla.nyartoolkit.java3d.utils.*;

import com.sun.j3d.utils.geometry.ColorCube;

/**
 * Java3Dã‚µãƒ³ãƒ—ãƒ«ãƒ—ãƒ­ã‚°ãƒ©ãƒ 
 * å�˜ä¸€ãƒžãƒ¼ã‚«ãƒ¼è¿½è·¡ç”¨ã�®Behaviorã‚’ä½¿ã�£ã�¦ã€�èƒŒæ™¯ã�¨ï¼‘å€‹ã�®ãƒžãƒ¼ã‚«ãƒ¼ã�«é€£å‹•ã�—ã�Ÿ
 * TransformGroupã‚’å‹•ã�‹ã�—ã�¾ã�™ã€‚
 *
 */
public class Simplejava3D extends JFrame implements NyARSingleMarkerBehaviorListener
{
	private static final long serialVersionUID = -8472866262481865377L;

	private final String CARCODE_FILE = "./Data/patt.hiro";

	private final String PARAM_FILE = "./Data/camera_para.dat";

	//NyARToolkité–¢ä¿‚
	private NyARSingleMarkerBehaviorHolder nya_behavior;

	private J3dNyARParam ar_param;

	//universeé–¢ä¿‚
	private Canvas3D canvas;

	private Locale locale;

	private VirtualUniverse universe;

	public static void main(String[] args)
	{
		try {
			Simplejava3D frame = new Simplejava3D();

			frame.setVisible(true);
			Insets ins = frame.getInsets();
			frame.setSize(800 + ins.left + ins.right, 600 + ins.top + ins.bottom);
			frame.startCapture();
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void onUpdate(boolean i_is_marker_exist, Transform3D i_transform3d)
	{
		/*
		 * TODO:Please write your behavior operation code here.
		 * ãƒžãƒ¼ã‚«ãƒ¼ã�®å§¿å‹¢ã‚’å…ƒã�«ä»–ã�®ï¼“Dã‚ªãƒ–ã‚¸ã‚§ã‚¯ãƒˆã‚’æ“�ä½œã�™ã‚‹ã�¨ã��ã�¯ã€�ã�“ã�“ã�«å‡¦ç�†ã‚’æ›¸ã��ã�¾ã�™ã€‚*/

	}

	public void startCapture() throws Exception
	{
		nya_behavior.start();
	}

	public Simplejava3D() throws Exception
	{
		super("Java3D Example NyARToolkit");

		//NyARToolkitã�®æº–å‚™
		NyARCode ar_code = new NyARCode(16, 16);
		ar_code.loadARPattFromFile(CARCODE_FILE);
		ar_param = new J3dNyARParam();
		ar_param.loadARParamFromFile(PARAM_FILE);
		ar_param.changeScreenSize(320, 240);

		//localeã�®ä½œæˆ�ã�¨locateã�¨viewã�®è¨­å®š
		universe = new VirtualUniverse();
		locale = new Locale(universe);
		canvas = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
		View view = new View();
		ViewPlatform viewPlatform = new ViewPlatform();
		view.attachViewPlatform(viewPlatform);
		view.addCanvas3D(canvas);
		view.setPhysicalBody(new PhysicalBody());
		view.setPhysicalEnvironment(new PhysicalEnvironment());

		//è¦–ç•Œã�®è¨­å®š(ã‚«ãƒ¡ãƒ©è¨­å®šã�‹ã‚‰å�–å¾—)
		Transform3D camera_3d = ar_param.getCameraTransform();
		view.setCompatibilityModeEnable(true);
		view.setProjectionPolicy(View.PERSPECTIVE_PROJECTION);
		view.setLeftProjection(camera_3d);

		//è¦–ç‚¹è¨­å®š(0,0,0ã�‹ã‚‰ã€�Yè»¸ã‚’180åº¦å›žè»¢ã�—ã�¦Z+æ–¹å�‘ã‚’å�‘ã��ã‚ˆã�†ã�«ã�™ã‚‹ã€‚)
		TransformGroup viewGroup = new TransformGroup();
		Transform3D viewTransform = new Transform3D();
		viewTransform.rotY(Math.PI);
		viewTransform.setTranslation(new Vector3d(0.0, 0.0, 0.0));
		viewGroup.setTransform(viewTransform);
		viewGroup.addChild(viewPlatform);
		BranchGroup viewRoot = new BranchGroup();
		viewRoot.addChild(viewGroup);
		locale.addBranchGraph(viewRoot);

		//ãƒ�ãƒƒã‚¯ã‚°ãƒ©ã‚¦ãƒ³ãƒ‰ã�®ä½œæˆ�
		Background background = new Background();
		BoundingSphere bounds = new BoundingSphere();
		bounds.setRadius(10.0);
		background.setApplicationBounds(bounds);
		background.setImageScaleMode(Background.SCALE_FIT_ALL);
		background.setCapability(Background.ALLOW_IMAGE_WRITE);
		BranchGroup root = new BranchGroup();
		root.addChild(background);

		//TransformGroupã�§å›²ã�£ã�Ÿã‚·ãƒ¼ãƒ³ã‚°ãƒ©ãƒ•ã�®ä½œæˆ�
		TransformGroup transform = new TransformGroup();
		transform.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		transform.addChild(createSceneGraph());
		root.addChild(transform);

		//NyARToolkitã�®Behaviorã‚’ä½œã‚‹ã€‚(ãƒžãƒ¼ã‚«ãƒ¼ã‚µã‚¤ã‚ºã�¯ãƒ¡ãƒ¼ãƒˆãƒ«ã�§æŒ‡å®šã�™ã‚‹ã�“ã�¨)
		nya_behavior = new NyARSingleMarkerBehaviorHolder(ar_param, 30f, ar_code, 0.08);
		//Behaviorã�«é€£å‹•ã�™ã‚‹ã‚°ãƒ«ãƒ¼ãƒ—ã‚’ã‚»ãƒƒãƒˆ
		nya_behavior.setTransformGroup(transform);
		nya_behavior.setBackGround(background);

		//å‡ºæ�¥ã�Ÿbehaviorã‚’ã‚»ãƒƒãƒˆ
		root.addChild(nya_behavior.getBehavior());
		nya_behavior.setUpdateListener(this);

		//è¡¨ç¤ºãƒ–ãƒ©ãƒ³ãƒ�ã‚’Locateã�«ã‚»ãƒƒãƒˆ
		locale.addBranchGraph(root);

		//ã‚¦ã‚¤ãƒ³ãƒ‰ã‚¦ã�®è¨­å®š
		setLayout(new BorderLayout());
		add(canvas, BorderLayout.CENTER);
	}

	/**
	 * ã‚·ãƒ¼ãƒ³ã‚°ãƒ©ãƒ•ã‚’ä½œã�£ã�¦ã€�ã��ã�®ãƒŽãƒ¼ãƒ‰ã‚’è¿”ã�™ã€‚
	 * ã�“ã�®ãƒŽãƒ¼ãƒ‰ã�¯40mmã�®è‰²ã�¤ã��ç«‹æ–¹ä½“ã‚’è¡¨ç¤ºã�™ã‚‹ã‚·ãƒ¼ãƒ³ã€‚ï½šè»¸ã‚’åŸºæº–ã�«20mmä¸Šã�«æµ®ã�‹ã�›ã�¦ã‚‹ã€‚
	 * @return
	 */
	private Node createSceneGraph()
	{
		TransformGroup tg = new TransformGroup();
		Transform3D mt = new Transform3D();
		mt.setTranslation(new Vector3d(0.00, 0.0, 20 * 0.001));
		// å¤§ã��ã�• 40mmã�®è‰²ä»˜ã��ç«‹æ–¹ä½“ã‚’ã€�Zè»¸ä¸Šã�§20mmå‹•ã�‹ã�—ã�¦é…�ç½®ï¼‰
		tg.setTransform(mt);
		tg.addChild(new ColorCube(20 * 0.001));
		return tg;
	}
}
