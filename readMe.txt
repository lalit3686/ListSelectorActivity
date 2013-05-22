ListSelectorActivity
====================

This demo shows how we can set the clicked item on ListView as selected.

Important things to keep in mind are,

1.) Set your ListView as ListView.CHOICE_MODE_SINGLE using mListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
2.) Create selector list_selector.xml and apply to parent layout of your row.xml

list_selector.xml

<?xml version="1.0" encoding="utf-8"?>
<selector xmlns:android="http://schemas.android.com/apk/res/android">
    <item android:drawable="@android:color/white" android:state_activated="true"/>
    <item android:drawable="@android:color/black"/>
</selector>


row.xml

<?xml version="1.0" encoding="utf-8"?>
<TextView xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@+id/mytxtview"
    android:layout_width="fill_parent"
    android:layout_height="wrap_content"
    android:gravity="center_vertical"
    android:minHeight="?android:attr/listPreferredItemHeight"
    android:paddingLeft="6dip"
    android:background="@drawable/list_selector"
    android:textColor="@drawable/text_view_colors"
    android:textAppearance="?android:attr/textAppearanceLarge" />


3.) And finally your ListView with cacheColorHint attribute in your main.xml as 

<ListView
   android:id="@+id/listView"
   android:layout_width="fill_parent"
   android:layout_height="fill_parent"
   android:cacheColorHint="@android:color/transparent" />


 Thats it!!!
 By doing the above changes will enable you to highlight the selected row and make it selected.
 