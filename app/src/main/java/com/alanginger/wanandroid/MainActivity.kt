package com.alanginger.wanandroid

import android.os.Bundle
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alanginger.wanandroid.model.Article
import com.alanginger.wanandroid.ui.home.HomeViewModel
import com.alanginger.wanandroid.ui.theme.Text_H1
import com.alanginger.wanandroid.ui.theme.Text_H2
import com.alanginger.wanandroid.ui.theme.WanAndroidComposeTheme

class MainActivity : ComponentActivity() {
    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp {
                Content(homeViewModel)
            }
        }
    }
}

@Composable
fun MyApp(content: @Composable () -> Unit) {
    WanAndroidComposeTheme {
        // A surface container using the 'background' color from the theme
        Surface(color = MaterialTheme.colors.background) {
            content()
        }
    }
}

@Composable
fun Content(viewModel: HomeViewModel) {
    val context = LocalContext.current
    val articleList = viewModel.articleListLiveData.observeAsState()
    Column {
        Row(
            Modifier
                .fillMaxWidth()
                .background(Color.LightGray)
        ) {
            BasicTextField(
                value = viewModel.keyword,
                onValueChange = {
                    viewModel.keyword = it
                },
                modifier = Modifier
                    .align(alignment = Alignment.CenterVertically)
                    .padding(start = 16.dp, end = 16.dp)
                    .weight(1f),
                textStyle = TextStyle(fontSize = 16.sp),
                singleLine = true
            )
            Icon(
                modifier = Modifier
                    .align(alignment = Alignment.CenterVertically)
                    .clickable {
                        Toast
                            .makeText(context, viewModel.keyword, LENGTH_SHORT)
                            .show()
                    }
                    .size(48.dp)
                    .padding(14.dp),
                painter = painterResource(id = android.R.drawable.ic_menu_search),
                contentDescription = "search",
                tint = Color.DarkGray
            )
        }
        LazyColumn(
            Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            items(articleList.value?.datas ?: arrayListOf()) { item: Article ->
                ArticleItem(item)
                Divider(Modifier.padding(16.dp, 0.dp), thickness = 0.5.dp)
            }
        }
    }
}

@Composable
fun ArticleItem(
    article: Article,
) {
    val context = LocalContext.current
    Box(modifier = Modifier
        .fillMaxWidth()
        .clickable {
            Toast
                .makeText(context, "web?url=${article.link}", LENGTH_SHORT)
                .show()
        }) {
        Column(
            Modifier.padding(16.dp, 10.dp)
        ) {
            Row(Modifier.fillMaxWidth()) {
                article.tags.forEach {
                    Text(
                        it.name,
                        Modifier
                            .align(Alignment.CenterVertically)
                            .border(0.5.dp, Text_H1, RoundedCornerShape(3.dp))
                            .padding(2.dp, 1.dp),
                        Text_H1,
                        10.sp
                    )
                    Spacer(
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .width(8.dp)
                            .height(0.dp)
                    )
                }
                Text(
                    article.getAuthor(),
                    Modifier
                        .weight(1f)
                        .align(Alignment.CenterVertically),
                    Text_H2,
                    12.sp
                )
                Spacer(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .width(10.dp)
                        .height(0.dp)
                )
                Text(
                    article.niceDate,
                    Modifier
                        .align(Alignment.CenterVertically),
                    Text_H2,
                    12.sp
                )
            }
            Text(
                article.title,
                Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                Text_H1,
                15.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp)
            ) {
                val chapter = StringBuilder(article.superChapterName)
                if (article.superChapterName.isNotEmpty() && article.chapterName.isNotEmpty()) {
                    chapter.append(" / ")
                }
                chapter.append(article.chapterName)
                Text(
                    chapter.toString(),
                    Modifier
                        .weight(1f)
                        .align(Alignment.CenterVertically),
                    Text_H2,
                    12.sp,
                )
            }
        }
    }
}