package com.jesusvilla.test.home.viewModels

import androidx.annotation.CheckResult
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jesusvilla.test.base.utils.UiText
import com.jesusvilla.test.base.viewModels.BaseViewModel
import com.jesusvilla.test.core.network.Session
import com.jesusvilla.test.database.di.qualifiers.PostRepositoryQualifier
import com.jesusvilla.test.database.repository.PostRepository
import com.jesusvilla.test.database.resources.doError
import com.jesusvilla.test.database.resources.doSuccess
import com.jesusvilla.test.home.di.GetPostUseCaseQualifier
import com.jesusvilla.test.home.ui.model.PostModel
import com.jesusvilla.test.home.ui.model.mapperToEntity
import com.jesusvilla.test.home.ui.model.mapperToModelUi
import com.jesusvilla.test.home.ui.model.mapperToUI
import com.jesusvilla.test.home.useCases.GetPostsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@Suppress("TooManyFunctions")
@HiltViewModel
class TestViewModel @Inject constructor(
    @PostRepositoryQualifier private val postRepository: PostRepository,
    @GetPostUseCaseQualifier private val getPostsUseCase: GetPostsUseCase,
) : BaseViewModel() {
    private var posts = MutableLiveData<List<PostModel>>()

    init {
        getPosts()
    }

    private fun getPosts() {
        if(!Session.isOnline) {
            _showNotificationToast.setValue(UiText.DynamicString("No hay conexión a internet, se obtendrá datos locales"))
            getLocalPost()
        } else {
            launcher(
                invoke = { getPostsUseCase() },
                responseResult = {
                    val list = it.mapperToUI()
                    saveLocalPost(list)
                    posts.setValue(list)
                }
            )
        }
    }

    private fun saveLocalPost(list: List<PostModel>) {
        viewModelScope.launch(Dispatchers.IO) {
            val data = postRepository.savePosts(list.mapperToEntity())
            data.doSuccess { list ->
            }
            data.doError {
                it.printStackTrace()
                _showNotificationToast.postValue(UiText.DynamicString("Error: al guardar datos locales: ${it.message}"))
            }
        }
    }

    private fun getLocalPost() {
        viewModelScope.launch() {
            val data = postRepository.getListPosts()
            data.doSuccess { list ->
                if(list.isEmpty()) {
                    _showNotificationToast.postValue(UiText.DynamicString("Error: no hay datos locales"))
                } else {
                    posts.postValue(list.mapperToModelUi())
                }
            }
            data.doError {
                _showNotificationToast.postValue(UiText.DynamicString("Error: no hay datos locales: ${it.message}"))
            }
        }
    }

    @CheckResult
    fun onPosts(): LiveData<List<PostModel>> = posts
}
