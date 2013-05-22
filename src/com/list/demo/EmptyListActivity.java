package com.list.demo;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.cyrilmottier.android.listviewtipsandtricks.R;

public class EmptyListActivity extends Activity {

	private static ArrayList<String> EMPTY = new ArrayList<String>();
	private ArrayList<String> CHEESES = new ArrayList<String>();
	private CheeseAdapter mAdapter;
	private ListView mListView;
	private ViewStub inflate_stub;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.empty_list);

		for (int i = 0; i < 20; i++) {
			CHEESES.add(i + "");
		}

		inflate_stub = (ViewStub) findViewById(R.id.inflate_stub);
		inflate_stub.inflate();
		inflate_stub.setVisibility(View.GONE);

		mListView = (ListView) findViewById(R.id.listView);
		mListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		mAdapter = new CheeseAdapter(CHEESES);
		mListView.setAdapter(mAdapter);
		mListView.setSelection(0);
		mListView.setItemChecked(0, true);
	}

	public void onSetEmpty(View v) {
		mAdapter.changeData(EMPTY);
	}

	public void onSetData(View v) {
		mAdapter.changeData(CHEESES);
	}

	private class CheeseAdapter extends BaseAdapter {

		private ArrayList<String> mData;

		public CheeseAdapter(ArrayList<String> data) {
			mData = data;
		}

		public void changeData(ArrayList<String> data) {
			mData = data;
			if (mData.size() > 0) {
				inflate_stub.setVisibility(View.GONE);
			} else {
				inflate_stub.setVisibility(View.VISIBLE);
			}
			notifyDataSetChanged();
		}

		@Override
		public int getCount() {
			return mData.size();
		}

		@Override
		public String getItem(int position) {
			return mData.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			if (convertView == null) {
				convertView = getLayoutInflater().inflate(R.layout.text_item,
						parent, false);
			}

			TextView textView = (TextView) convertView.findViewById(R.id.mytxtview);;
			textView.setText(getItem(position));

			return convertView;
		}
	}
}

/*
 * public class EmptyListActivity extends Activity { ListView myListView;
 * ArrayList<String> elements;
 * 
 * @Override public void onCreate(Bundle savedInstanceState) {
 * super.onCreate(savedInstanceState); setContentView(R.layout.empty_list);
 * 
 * // elements String s = "MNBVCXZLKJHGFDSAQWERTYUIOP"; Random r = new Random();
 * elements = new ArrayList<String>(); for (int i = 0; i < 300; i++) {
 * elements.add(s.substring(r.nextInt(s.length()))); }
 * Collections.sort(elements); // Must be sorted!
 * 
 * // listview myListView = (ListView) findViewById(R.id.listView);
 * myListView.setFastScrollEnabled(true); MyAZAdapter<String> adapter = new
 * MyAZAdapter<String>( getApplicationContext(),
 * android.R.layout.simple_list_item_1, elements);
 * myListView.setAdapter(adapter);
 * 
 * }
 * 
 * class MyAZAdapter<T> extends ArrayAdapter<String> implements SectionIndexer {
 * ArrayList<String> myElements; HashMap<String, Integer> azindexer; String[]
 * sections;
 * 
 * public MyAZAdapter(Context context, int textViewResourceId, ArrayList<String>
 * objects) { super(context, textViewResourceId, objects); myElements =
 * (ArrayList<String>) objects; azindexer = new HashMap<String, Integer>(); //
 * stores the // positions for // the start of // each letter
 * 
 * int size = elements.size(); for (int i = size - 1; i >= 0; i--) { String
 * element = elements.get(i); // We store the first letter of the word, and its
 * index. azindexer.put(element.substring(0, 1), i); }
 * 
 * Set<String> keys = azindexer.keySet(); // set of letters
 * 
 * Iterator<String> it = keys.iterator(); ArrayList<String> keyList = new
 * ArrayList<String>();
 * 
 * while (it.hasNext()) { String key = it.next(); keyList.add(key); }
 * Collections.sort(keyList);// sort the keylist sections = new
 * String[keyList.size()]; // simple conversion to array
 * keyList.toArray(sections); }
 * 
 * public int getPositionForSection(int section) { String letter =
 * sections[section]; return azindexer.get(letter); }
 * 
 * public int getSectionForPosition(int position) {
 * Log.v("getSectionForPosition", "called"); return 0; }
 * 
 * public Object[] getSections() { return sections; // to string will be called
 * to display the letter } } }
 */

/*
 * public class EmptyListActivity extends ListActivity implements
 * ListView.OnScrollListener {
 * 
 * private final class RemoveWindow implements Runnable { public void run() {
 * removeWindow(); } }
 * 
 * private RemoveWindow mRemoveWindow = new RemoveWindow(); Handler mHandler =
 * new Handler(); private WindowManager mWindowManager; private TextView
 * mDialogText; private boolean mShowing; private boolean mReady; private char
 * mPrevLetter = Character.MIN_VALUE;
 * 
 * @Override public void onCreate(Bundle savedInstanceState) {
 * super.onCreate(savedInstanceState);
 * 
 * mWindowManager = (WindowManager)getSystemService(Context.WINDOW_SERVICE);
 * 
 * // Use an existing ListAdapter that will map an array // of strings to
 * TextViews setListAdapter(new ArrayAdapter<String>(this,
 * android.R.layout.simple_list_item_1, mStrings));
 * 
 * getListView().setOnScrollListener(this);
 * 
 * mDialogText = new TextView(this); mDialogText.setVisibility(View.INVISIBLE);
 * 
 * mHandler.post(new Runnable() { public void run() { mReady = true;
 * WindowManager.LayoutParams lp = new WindowManager.LayoutParams(
 * LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,
 * WindowManager.LayoutParams.TYPE_APPLICATION,
 * WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE |
 * WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, PixelFormat.TRANSLUCENT);
 * mWindowManager.addView(mDialogText, lp); } }); }
 * 
 * @Override protected void onResume() { super.onResume(); mReady = true; }
 * 
 * 
 * @Override protected void onPause() { super.onPause(); removeWindow(); mReady
 * = false; }
 * 
 * @Override protected void onDestroy() { super.onDestroy(); if (mReady) {
 * mWindowManager.removeView(mDialogText); } mReady = false; }
 * 
 * public void onScroll(AbsListView view, int firstVisibleItem, int
 * visibleItemCount, int totalItemCount) {
 * 
 * if (mReady) { char firstLetter = ' '; final String text =
 * mStrings[firstVisibleItem]; if (!TextUtils.isEmpty(text)) { firstLetter =
 * text.charAt(0); }
 * 
 * if (!mShowing && firstLetter != mPrevLetter) { mShowing = true;
 * mDialogText.setVisibility(View.VISIBLE); }
 * 
 * mDialogText.setText(((Character)firstLetter).toString());
 * mHandler.removeCallbacks(mRemoveWindow); mHandler.postDelayed(mRemoveWindow,
 * 3000); mPrevLetter = firstLetter; } }
 * 
 * public void onScrollStateChanged(AbsListView view, int scrollState) { }
 * 
 * private void removeWindow() { if (mShowing) { mShowing = false;
 * mDialogText.setVisibility(View.INVISIBLE); } }
 * 
 * private String[] mStrings = { "Abbaye de Belloc", "Abbaye du Mont des Cats",
 * "Abertam", "Abondance", "Ackawi", "Acorn", "Adelost",
 * "Affidelice au Chablis", "Afuega'l Pitu", "Airag", "Airedale", "Aisy Cendre",
 * "Allgauer Emmentaler", "Alverca", "Ambert", "American Cheese",
 * "Ami du Chambertin", "Anejo Enchilado", "Anneau du Vic-Bilh", "Anthoriro",
 * "Appenzell", "Aragon", "Ardi Gasna", "Ardrahan", "Armenian String",
 * "Aromes au Gene de Marc", "Asadero", "Asiago", "Aubisque Pyrenees", "Autun",
 * "Avaxtskyr", "Baby Swiss", "Babybel", "Baguette Laonnaise", "Bakers",
 * "Baladi", "Balaton", "Bandal", "Banon", "Barry's Bay Cheddar", "Basing",
 * "Basket Cheese", "Bath Cheese", "Bavarian Bergkase", "Baylough", "Beaufort",
 * "Beauvoorde", "Beenleigh Blue", "Beer Cheese", "Bel Paese", "Bergader",
 * "Bergere Bleue", "Berkswell", "Beyaz Peynir", "Bierkase", "Bishop Kennedy",
 * "Blarney", "Bleu d'Auvergne", "Bleu de Gex", "Bleu de Laqueuille",
 * "Bleu de Septmoncel", "Bleu Des Causses", "Blue", "Blue Castello",
 * "Blue Rathgore", "Blue Vein (Australian)", "Blue Vein Cheeses", "Bocconcini",
 * "Bocconcini (Australian)", "Boeren Leidenkaas", "Bonchester", "Bosworth",
 * "Bougon", "Boule Du Roves", "Boulette d'Avesnes", "Boursault", "Boursin",
 * "Bouyssou", "Bra", "Braudostur", "Breakfast Cheese", "Brebis du Lavort",
 * "Brebis du Lochois", "Brebis du Puyfaucon", "Bresse Bleu", "Brick", "Brie",
 * "Brie de Meaux", "Brie de Melun", "Brillat-Savarin", "Brin", "Brin d' Amour",
 * "Brin d'Amour", "Brinza (Burduf Brinza)", "Briquette de Brebis",
 * "Briquette du Forez", "Broccio", "Broccio Demi-Affine", "Brousse du Rove",
 * "Bruder Basil", "Brusselae Kaas (Fromage de Bruxelles)", "Bryndza",
 * "Buchette d'Anjou", "Buffalo", "Burgos", "Butte", "Butterkase",
 * "Button (Innes)", "Buxton Blue", "Cabecou", "Caboc", "Cabrales", "Cachaille",
 * "Caciocavallo", "Caciotta", "Caerphilly", "Cairnsmore", "Calenzana",
 * "Cambazola", "Camembert de Normandie", "Canadian Cheddar", "Canestrato",
 * "Cantal", "Caprice des Dieux", "Capricorn Goat", "Capriole Banon",
 * "Carre de l'Est", "Casciotta di Urbino", "Cashel Blue", "Castellano",
 * "Castelleno", "Castelmagno", "Castelo Branco", "Castigliano", "Cathelain",
 * "Celtic Promise", "Cendre d'Olivet", "Cerney", "Chabichou",
 * "Chabichou du Poitou", "Chabis de Gatine", "Chaource", "Charolais",
 * "Chaumes", "Cheddar", "Cheddar Clothbound", "Cheshire", "Chevres",
 * "Chevrotin des Aravis", "Chontaleno", "Civray",
 * "Coeur de Camembert au Calvados", "Coeur de Chevre", "Colby", "Cold Pack",
 * "Comte", "Coolea", "Cooleney", "Coquetdale", "Corleggy", "Cornish Pepper",
 * "Cotherstone", "Cotija", "Cottage Cheese", "Cottage Cheese (Australian)",
 * "Cougar Gold", "Coulommiers", "Coverdale", "Crayeux de Roncq",
 * "Cream Cheese", "Cream Havarti", "Crema Agria", "Crema Mexicana",
 * "Creme Fraiche", "Crescenza", "Croghan", "Crottin de Chavignol",
 * "Crottin du Chavignol", "Crowdie", "Crowley", "Cuajada", "Curd",
 * "Cure Nantais", "Curworthy", "Cwmtawe Pecorino", "Cypress Grove Chevre",
 * "Danablu (Danish Blue)", "Danbo", "Danish Fontina", "Daralagjazsky",
 * "Dauphin", "Delice des Fiouves", "Denhany Dorset Drum", "Derby",
 * "Dessertnyj Belyj", "Devon Blue", "Devon Garland", "Dolcelatte", "Doolin",
 * "Doppelrhamstufel", "Dorset Blue Vinney", "Double Gloucester",
 * "Double Worcester", "Dreux a la Feuille", "Dry Jack", "Duddleswell",
 * "Dunbarra", "Dunlop", "Dunsyre Blue", "Duroblando", "Durrus",
 * "Dutch Mimolette (Commissiekaas)", "Edam", "Edelpilz", "Emental Grand Cru",
 * "Emlett", "Emmental", "Epoisses de Bourgogne", "Esbareich", "Esrom",
 * "Etorki", "Evansdale Farmhouse Brie", "Evora De L'Alentejo", "Exmoor Blue",
 * "Explorateur", "Feta", "Feta (Australian)", "Figue", "Filetta",
 * "Fin-de-Siecle", "Finlandia Swiss", "Finn", "Fiore Sardo", "Fleur du Maquis",
 * "Flor de Guia", "Flower Marie", "Folded", "Folded cheese with mint",
 * "Fondant de Brebis", "Fontainebleau", "Fontal", "Fontina Val d'Aosta",
 * "Formaggio di capra", "Fougerus", "Four Herb Gouda", "Fourme d' Ambert",
 * "Fourme de Haute Loire", "Fourme de Montbrison", "Fresh Jack",
 * "Fresh Mozzarella", "Fresh Ricotta", "Fresh Truffles", "Fribourgeois",
 * "Friesekaas", "Friesian", "Friesla", "Frinault", "Fromage a Raclette",
 * "Fromage Corse", "Fromage de Montagne de Savoie", "Fromage Frais",
 * "Fruit Cream Cheese", "Frying Cheese", "Fynbo", "Gabriel",
 * "Galette du Paludier", "Galette Lyonnaise", "Galloway Goat's Milk Gems",
 * "Gammelost", "Gaperon a l'Ail", "Garrotxa", "Gastanberra", "Geitost",
 * "Gippsland Blue", "Gjetost", "Gloucester", "Golden Cross", "Gorgonzola",
 * "Gornyaltajski", "Gospel Green", "Gouda", "Goutu", "Gowrie", "Grabetto",
 * "Graddost", "Grafton Village Cheddar", "Grana", "Grana Padano",
 * "Grand Vatel", "Grataron d' Areches", "Gratte-Paille", "Graviera", "Greuilh",
 * "Greve", "Gris de Lille", "Gruyere", "Gubbeen", "Guerbigny", "Halloumi",
 * "Halloumy (Australian)", "Haloumi-Style Cheese", "Harbourne Blue", "Havarti",
 * "Heidi Gruyere", "Hereford Hop", "Herrgardsost", "Herriot Farmhouse",
 * "Herve", "Hipi Iti", "Hubbardston Blue Cow", "Hushallsost", "Iberico",
 * "Idaho Goatster", "Idiazabal", "Il Boschetto al Tartufo", "Ile d'Yeu",
 * "Isle of Mull", "Jarlsberg", "Jermi Tortes", "Jibneh Arabieh", "Jindi Brie",
 * "Jubilee Blue", "Juustoleipa", "Kadchgall", "Kaseri", "Kashta", "Kefalotyri",
 * "Kenafa", "Kernhem", "Kervella Affine", "Kikorangi",
 * "King Island Cape Wickham Brie", "King River Gold", "Klosterkaese",
 * "Knockalara", "Kugelkase", "L'Aveyronnais", "L'Ecir de l'Aubrac",
 * "La Taupiniere", "La Vache Qui Rit", "Laguiole", "Lairobell", "Lajta",
 * "Lanark Blue", "Lancashire", "Langres", "Lappi", "Laruns", "Lavistown",
 * "Le Brin", "Le Fium Orbo", "Le Lacandou", "Le Roule", "Leafield", "Lebbene",
 * "Leerdammer", "Leicester", "Leyden", "Limburger", "Lincolnshire Poacher",
 * "Lingot Saint Bousquet d'Orb", "Liptauer", "Little Rydings", "Livarot",
 * "Llanboidy", "Llanglofan Farmhouse", "Loch Arthur Farmhouse",
 * "Loddiswell Avondale", "Longhorn", "Lou Palou", "Lou Pevre", "Lyonnais",
 * "Maasdam", "Macconais", "Mahoe Aged Gouda", "Mahon", "Malvern", "Mamirolle",
 * "Manchego", "Manouri", "Manur", "Marble Cheddar", "Marbled Cheeses",
 * "Maredsous", "Margotin", "Maribo", "Maroilles", "Mascares", "Mascarpone",
 * "Mascarpone (Australian)", "Mascarpone Torta", "Matocq", "Maytag Blue",
 * "Meira", "Menallack Farmhouse", "Menonita", "Meredith Blue", "Mesost",
 * "Metton (Cancoillotte)", "Meyer Vintage Gouda", "Mihalic Peynir", "Milleens",
 * "Mimolette", "Mine-Gabhar", "Mini Baby Bells", "Mixte", "Molbo",
 * "Monastery Cheeses", "Mondseer", "Mont D'or Lyonnais", "Montasio",
 * "Monterey Jack", "Monterey Jack Dry", "Morbier", "Morbier Cru de Montagne",
 * "Mothais a la Feuille", "Mozzarella", "Mozzarella (Australian)",
 * "Mozzarella di Bufala", "Mozzarella Fresh, in water", "Mozzarella Rolls",
 * "Munster", "Murol", "Mycella", "Myzithra", "Naboulsi", "Nantais",
 * "Neufchatel", "Neufchatel (Australian)", "Niolo", "Nokkelost",
 * "Northumberland", "Oaxaca", "Olde York", "Olivet au Foin", "Olivet Bleu",
 * "Olivet Cendre", "Orkney Extra Mature Cheddar", "Orla", "Oschtjepka",
 * "Ossau Fermier", "Ossau-Iraty", "Oszczypek", "Oxford Blue",
 * "P'tit Berrichon", "Palet de Babligny", "Paneer", "Panela", "Pannerone",
 * "Pant ys Gawn", "Parmesan (Parmigiano)", "Parmigiano Reggiano",
 * "Pas de l'Escalette", "Passendale", "Pasteurized Processed",
 * "Pate de Fromage", "Patefine Fort", "Pave d'Affinois", "Pave d'Auge",
 * "Pave de Chirac", "Pave du Berry", "Pecorino", "Pecorino in Walnut Leaves",
 * "Pecorino Romano", "Peekskill Pyramid", "Pelardon des Cevennes",
 * "Pelardon des Corbieres", "Penamellera", "Penbryn", "Pencarreg",
 * "Perail de Brebis", "Petit Morin", "Petit Pardou", "Petit-Suisse",
 * "Picodon de Chevre", "Picos de Europa", "Piora", "Pithtviers au Foin",
 * "Plateau de Herve", "Plymouth Cheese", "Podhalanski", "Poivre d'Ane",
 * "Polkolbin", "Pont l'Eveque", "Port Nicholson", "Port-Salut", "Postel",
 * "Pouligny-Saint-Pierre", "Pourly", "Prastost", "Pressato", "Prince-Jean",
 * "Processed Cheddar", "Provolone", "Provolone (Australian)",
 * "Pyengana Cheddar", "Pyramide", "Quark", "Quark (Australian)",
 * "Quartirolo Lombardo", "Quatre-Vents", "Quercy Petit", "Queso Blanco",
 * "Queso Blanco con Frutas --Pina y Mango", "Queso de Murcia",
 * "Queso del Montsec", "Queso del Tietar", "Queso Fresco",
 * "Queso Fresco (Adobera)", "Queso Iberico", "Queso Jalapeno",
 * "Queso Majorero", "Queso Media Luna", "Queso Para Frier", "Queso Quesadilla",
 * "Rabacal", "Raclette", "Ragusano", "Raschera", "Reblochon", "Red Leicester",
 * "Regal de la Dombes", "Reggianito", "Remedou", "Requeson", "Richelieu",
 * "Ricotta", "Ricotta (Australian)", "Ricotta Salata", "Ridder", "Rigotte",
 * "Rocamadour", "Rollot", "Romano", "Romans Part Dieu", "Roncal", "Roquefort",
 * "Roule", "Rouleau De Beaulieu", "Royalp Tilsit", "Rubens", "Rustinu",
 * "Saaland Pfarr", "Saanenkaese", "Saga", "Sage Derby", "Sainte Maure",
 * "Saint-Marcellin", "Saint-Nectaire", "Saint-Paulin", "Salers", "Samso",
 * "San Simon", "Sancerre", "Sap Sago", "Sardo", "Sardo Egyptian", "Sbrinz",
 * "Scamorza", "Schabzieger", "Schloss", "Selles sur Cher", "Selva", "Serat",
 * "Seriously Strong Cheddar", "Serra da Estrela", "Sharpam",
 * "Shelburne Cheddar", "Shropshire Blue", "Siraz", "Sirene", "Smoked Gouda",
 * "Somerset Brie", "Sonoma Jack", "Sottocenare al Tartufo", "Soumaintrain",
 * "Sourire Lozerien", "Spenwood", "Sraffordshire Organic",
 * "St. Agur Blue Cheese", "Stilton", "Stinking Bishop", "String",
 * "Sussex Slipcote", "Sveciaost", "Swaledale", "Sweet Style Swiss", "Swiss",
 * "Syrian (Armenian String)", "Tala", "Taleggio", "Tamie",
 * "Tasmania Highland Chevre Log", "Taupiniere", "Teifi", "Telemea", "Testouri",
 * "Tete de Moine", "Tetilla", "Texas Goat Cheese", "Tibet",
 * "Tillamook Cheddar", "Tilsit", "Timboon Brie", "Toma", "Tomme Brulee",
 * "Tomme d'Abondance", "Tomme de Chevre", "Tomme de Romans", "Tomme de Savoie",
 * "Tomme des Chouans", "Tommes", "Torta del Casar", "Toscanello",
 * "Touree de L'Aubier", "Tourmalet", "Trappe (Veritable)",
 * "Trois Cornes De Vendee", "Tronchon", "Trou du Cru", "Truffe", "Tupi",
 * "Turunmaa", "Tymsboro", "Tyn Grug", "Tyning", "Ubriaco", "Ulloa",
 * "Vacherin-Fribourgeois", "Valencay", "Vasterbottenost", "Venaco",
 * "Vendomois", "Vieux Corse", "Vignotte", "Vulscombe",
 * "Waimata Farmhouse Blue", "Washed Rind Cheese (Australian)", "Waterloo",
 * "Weichkaese", "Wellington", "Wensleydale", "White Stilton",
 * "Whitestone Farmhouse", "Wigmore", "Woodside Cabecou", "Xanadu", "Xynotyro",
 * "Yarg Cornish", "Yarra Valley Pyramid", "Yorkshire Blue", "Zamorano",
 * "Zanetti Grana Padano", "Zanetti Parmigiano Reggiano"};
 * 
 * }
 */