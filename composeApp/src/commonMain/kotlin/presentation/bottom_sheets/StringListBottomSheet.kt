package presentation.bottom_sheets

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import models.DataXXX
import models.InsuranceTypeCodeModel
import utils.AppConstants.Companion.getOutlineTextFieldColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(
    data: DataXXX? = DataXXX(),
    onDismiss: () -> Unit,
    onSelected: (selectedData: InsuranceTypeCodeModel) -> Unit
) {

    var searchQuery by remember { mutableStateOf("") }

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    val filteredList = remember(searchQuery) {
        data?.insuranceTypeCodeModels?.filter {
            it?.description!!.en.contains(
                searchQuery,
                ignoreCase = true
            )
        } as ArrayList
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
                .fillMaxHeight(0.8f)
        ) {
            Text("DOB Month", fontSize = 18.sp, color = Color.Black, fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(16.dp))


            // Search Field
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Search...") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Icon"
                    )
                },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { searchQuery = "" }) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = "Clear Icon"
                            )
                        }
                    }
                },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                colors = getOutlineTextFieldColors(),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        MaterialTheme.colorScheme.surfaceVariant,
                        RoundedCornerShape(12.dp)
                    )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Filtered List
            LazyColumn(
                modifier = Modifier.fillMaxWidth()
            ) {
                if (!filteredList.isNullOrEmpty()) {
                    items(filteredList.size) { pos ->

                        if (data?.id == 29) {

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                                    .clickable {

                                        filteredList[pos]?.let {
                                            it.isChecked = !it.isChecked
                                        }

                                    }) {
                                Checkbox(
                                    checked = filteredList[pos]?.isChecked ?: false,
                                    onCheckedChange = {
                                        check(it)
                                        filteredList[pos]?.isChecked = it
                                    }
                                )

                                Text(
                                    text = filteredList[pos]?.description?.en ?: "",
                                    style = MaterialTheme.typography.bodyLarge,
                                    modifier = Modifier
                                        .align(Alignment.CenterVertically)
                                        .fillMaxWidth()
                                        .padding(vertical = 20.dp)
                                )
                            }
                        } else {
                            Box(modifier = Modifier.fillMaxWidth().clickable {
                                println("Selected: $pos")
                                filteredList[pos]?.let { onSelected(it) }
                            }) {
                                Text(
                                    text = filteredList[pos]?.description!!.en,
                                    style = MaterialTheme.typography.bodyLarge,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 20.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

fun getSelectedSpecificationList(data: DataXXX): List<InsuranceTypeCodeModel> {
    val selectedList: List<InsuranceTypeCodeModel> = ArrayList()
    data.insuranceTypeCodeModels?.forEach {
        if (it != null) {
            if (it.isChecked)
                selectedList.plus(it)
        }
    }
    return selectedList
}

