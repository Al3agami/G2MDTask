package com.agamidev.g2mdtask.Interfaces;

import android.widget.SimpleCursorAdapter;

public interface ContactsContract {
    interface ContactsPresenterInterface{
        void loadContacts();
    }


    interface ContactsView{
        void contactsLoadedSuccessfully(SimpleCursorAdapter simpleCursorAdapter);
    }

    interface ContactsInteractorInterface{
        interface OnLoadedContactsFinished{
            void loadFinished(SimpleCursorAdapter simpleCursorAdapter);
        }
        void loadContacts(OnLoadedContactsFinished onLoadedContactsFinished);
    }

}
