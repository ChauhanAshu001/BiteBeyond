package com.nativenomad.adminbitebeyond.data.repository

import com.nativenomad.adminbitebeyond.domain.repository.RestaurantMenuRepo
import com.nativenomad.adminbitebeyond.domain.usecases.databaseOp.DatabaseOpUseCases
import com.nativenomad.adminbitebeyond.models.Menu
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class RestaurantMenuRepoImpl(private val databaseOpUseCases: DatabaseOpUseCases):RestaurantMenuRepo {

    private val _menu=MutableStateFlow<List<Menu>>(emptyList())
    override val menu=_menu

    init{
       CoroutineScope(Dispatchers.IO).launch {
           databaseOpUseCases.getFullMenu().collect{
               _menu.value=it
           }
       }
    }


    override suspend fun addItem(menuItem:Menu){
        val newItem=Menu(name=menuItem.name,imageUrl=menuItem.imageUrl,cost=menuItem.cost)
        val currentList=_menu.value.toMutableList()
        currentList.add(newItem)
        _menu.value=currentList

        databaseOpUseCases.addMenuItem(menuItem)
    }

    override suspend fun removeItem(menuItem: Menu) {
        val currentList=_menu.value.toMutableList()
        currentList.remove(menuItem)
        _menu.value=currentList

       databaseOpUseCases.removeMenuItem(menuItem)
       databaseOpUseCases.removeCategoryGlobally(menuItem)

    }

    override suspend fun addCategoryGlobally(menuItem: Menu) {
        databaseOpUseCases.saveCategoriesGlobally(menuItem)
    }

}