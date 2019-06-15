package com.agamidev.g2mdtask.ContactsListFiles;

import android.widget.SimpleCursorAdapter;

import com.agamidev.g2mdtask.Interfaces.ContactsContract;

public class ContactsPresenter implements ContactsContract.ContactsPresenterInterface, ContactsContract.ContactsInteractorInterface.OnLoadedContactsFinished {

    ContactsContract.ContactsInteractorInterface contactsInteractor;
    ContactsContract.ContactsView contactsView;

    public ContactsPresenter(ContactsContract.ContactsView contactsView, ContactsContract.ContactsInteractorInterface contactsInteractor){
        this.contactsView = contactsView;
        this.contactsInteractor = contactsInteractor;
    }

    @Override
    public void loadContacts() {
        contactsInteractor.loadContacts(this);
    }

    @Override
    public void loadFinished(SimpleCursorAdapter simpleCursorAdapter) {
        contactsView.contactsLoadedSuccessfully(simpleCursorAdapter);
    }
}
