import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import olyarisu.github.com.myapplication.data.datasource.DefaultLocalRedditDatasource
import olyarisu.github.com.myapplication.data.datasource.DefaultRemoteRedditDataSource
import olyarisu.github.com.myapplication.data.repository.DefaultSubredditRepository
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

//TODO fix test with coroutines

/*
class DefaultSubredditRepositorySpek : Spek({

    val localRedditDatasourceMock = mock<DefaultLocalRedditDatasource>()
    val remoteRedditDataSourceMock = mock<DefaultRemoteRedditDataSource>()

    val repository =
        DefaultSubredditRepository(localRedditDatasourceMock, remoteRedditDataSourceMock)

    describe("repository refresh") {
        repository.refresh("gaming")

        it("should call loadPosts for remoteRedditDataSource") {
            verify(remoteRedditDataSourceMock).loadPosts(eq("gaming"), any(), any())
        }
    }

    describe("repository retryLastFailedOperation") {
        repository.retryLastFailedOperation()

        it("should call retryFailed for remoteRedditDataSource") {
            verify(remoteRedditDataSourceMock).retryFailed()
        }
    }

})*/
